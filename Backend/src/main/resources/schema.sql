-- ======================================
-- 1. Tabelas
-- ======================================

-- Tabela Perfil
CREATE TABLE Perfil (
    IDPerfil INT AUTO_INCREMENT PRIMARY KEY,
    Tipo VARCHAR(20) NOT NULL -- Gerente, Dev, QA, Security
);

-- Tabela Pessoa (removendo IDPerfil para relacionamento N:N)
CREATE TABLE Pessoa (
    IDPessoa INT AUTO_INCREMENT PRIMARY KEY,
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
    IDContrato INT AUTO_INCREMENT PRIMARY KEY,
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
    IDProjeto INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(100) NOT NULL,
    DataInicioProj DATE NOT NULL,
    DataFimProj DATE NULL,
    Descricao VARCHAR(255) NULL
);

-- Tabela Alocacao (adicionando IDPessoa)
CREATE TABLE Alocacao (
    IDAlocacao INT AUTO_INCREMENT PRIMARY KEY,
    IDProjeto INT NOT NULL,
    IDContrato INT NOT NULL,
    IDPessoa INT NOT NULL,
    horaSemana INT NOT NULL CHECK (horaSemana >= 0 AND horaSemana <= 40), -- CHECK CONSTRAINT para hora semanal
    FOREIGN KEY (IDProjeto) REFERENCES Projeto(IDProjeto),
    FOREIGN KEY (IDContrato) REFERENCES Contrato(IDContrato),
    FOREIGN KEY (IDPessoa) REFERENCES Pessoa(IDPessoa),
    CONSTRAINT uq_pessoa_projeto UNIQUE (IDProjeto, IDContrato) -- garante que um contrato (perfil/pessoa) não repita no mesmo projeto
);

