-- ======================================
-- 1. Tabelas
-- ======================================

-- Tabela Perfil
CREATE TABLE Perfil (
    IDPerfil INT IDENTITY(1,1) PRIMARY KEY,
    Tipo VARCHAR(20) NOT NULL -- Gerente, Dev, QA, Security
);

-- Tabela Pessoa (removendo IDPerfil para relacionamento N:N)
CREATE TABLE Pessoa (
    IDPessoa INT IDENTITY(1,1) PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL
);

-- Tabela intermediária para relacionamento N:N entre Pessoa e Perfil
CREATE TABLE PessoaPerfil (
    IDPessoa INT NOT NULL,
    IDPerfil INT NOT NULL,
    PRIMARY KEY (IDPessoa, IDPerfil),
    FOREIGN KEY (IDPessoa) REFERENCES Pessoa(IDPessoa),
    FOREIGN KEY (IDPerfil) REFERENCES Perfil(IDPerfil)
);

-- Tabela Contrato
CREATE TABLE Contrato (
    IDContrato INT IDENTITY(1,1) PRIMARY KEY,
    IDPessoa INT NOT NULL,
    IDPerfil INT NOT NULL,
    DataInicioContrato DATE NOT NULL,
    DataFimContrato DATE NOT NULL,
    NumHoraSemanal INT NOT NULL CHECK (NumHoraSemanal >= 0),
    SalarioHora DECIMAL(10,2) NOT NULL CHECK (SalarioHora >= 0),
    FOREIGN KEY (IDPessoa) REFERENCES Pessoa(IDPessoa),
    FOREIGN KEY (IDPerfil) REFERENCES Perfil(IDPerfil)
);

-- Tabela Projeto
CREATE TABLE Projeto (
    IDProjeto INT IDENTITY(1,1) PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    DataInicioProj DATE NOT NULL,
    DataFimProj DATE NULL,
    Descricao VARCHAR(255) NULL
);

-- Tabela Alocacao (adicionando IDPessoa)
CREATE TABLE Alocacao (
    IDAlocacao INT IDENTITY(1,1) PRIMARY KEY,
    IDProjeto INT NOT NULL,
    IDContrato INT NOT NULL,
    IDPessoa INT NOT NULL,
    horaSemana INT NOT NULL CHECK (horaSemana >= 0 AND horaSemana <= 40), -- CHECK CONSTRAINT para hora semanal
    FOREIGN KEY (IDProjeto) REFERENCES Projeto(IDProjeto),
    FOREIGN KEY (IDContrato) REFERENCES Contrato(IDContrato),
    FOREIGN KEY (IDPessoa) REFERENCES Pessoa(IDPessoa),
    CONSTRAINT uq_pessoa_projeto UNIQUE (IDProjeto, IDContrato) -- garante que um contrato (perfil/pessoa) não repita no mesmo projeto
);

-- ======================================
-- 2. Triggers 
-- ======================================

-- Trigger: garante exatamente 1 gerente, pelo menos 1 Dev e 1 QA
CREATE TRIGGER trg_valida_perfis_projeto
ON Alocacao
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    IF EXISTS (
        SELECT a.IDProjeto
        FROM Alocacao a
        JOIN Contrato c ON a.IDContrato = c.IDContrato
        JOIN Perfil p ON c.IDPerfil = p.IDPerfil
        WHERE a.IDProjeto IN (SELECT IDProjeto FROM inserted
                              UNION
                              SELECT IDProjeto FROM deleted)
        GROUP BY a.IDProjeto
        HAVING SUM(CASE WHEN p.Tipo = 'Gerente' THEN 1 ELSE 0 END) <> 1
           OR SUM(CASE WHEN p.Tipo = 'Dev' THEN 1 ELSE 0 END) < 1
           OR SUM(CASE WHEN p.Tipo = 'QA' THEN 1 ELSE 0 END) < 1
    )
    BEGIN
        RAISERROR('Cada projeto deve ter exatamente 1 Gerente, pelo menos 1 Dev e 1 QA.',16,1);
        ROLLBACK TRANSACTION;
        RETURN;
    END
END;

-- ======================================
-- 3. Cálculo de custo do projeto
-- ======================================

CREATE PROCEDURE CalcularCustoProjeto
    @IDProjeto INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Custo total do projeto
    SELECT 
        p.Nome AS Projeto,
        SUM(a.horaSemana * c.SalarioHora) AS CustoTotal
    FROM Alocacao a
    JOIN Contrato c ON a.IDContrato = c.IDContrato
    JOIN Projeto p ON a.IDProjeto = p.IDProjeto
    WHERE a.IDProjeto = @IDProjeto
    GROUP BY p.Nome;

    -- Custo por pessoa/perfil
    SELECT 
        c.IDPessoa,
        p.Nome AS Projeto,
        pf.Tipo AS Perfil,
        SUM(a.horaSemana * c.SalarioHora) AS CustoPorPessoa
    FROM Alocacao a
    JOIN Contrato c ON a.IDContrato = c.IDContrato
    JOIN Projeto p ON a.IDProjeto = p.IDProjeto
    JOIN Perfil pf ON c.IDPerfil = pf.IDPerfil
    WHERE a.IDProjeto = @IDProjeto
    GROUP BY c.IDPessoa, p.Nome, pf.Tipo;
END;
