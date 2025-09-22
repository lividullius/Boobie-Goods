-- ======================================
-- Inserts de Dados
-- ======================================

-- Perfis
INSERT INTO Perfil (Tipo) VALUES ('Gerente');
INSERT INTO Perfil (Tipo) VALUES ('Dev');
INSERT INTO Perfil (Tipo) VALUES ('QA');
INSERT INTO Perfil (Tipo) VALUES ('Security');

-- Pessoas
INSERT INTO Pessoa (Nome, IDPerfil) VALUES ('Ana Souza', 1);   -- Gerente
INSERT INTO Pessoa (Nome, IDPerfil) VALUES ('Carlos Lima', 2); -- Dev
INSERT INTO Pessoa (Nome, IDPerfil) VALUES ('Fernanda Alves', 2); -- Dev
INSERT INTO Pessoa (Nome, IDPerfil) VALUES ('Rafael Costa', 3);   -- QA
INSERT INTO Pessoa (Nome, IDPerfil) VALUES ('Juliana Torres', 4); -- Security

-- Contratos (agora com DataFimContrato obrigatória)
INSERT INTO Contrato (IDPessoa, IDPerfil, DataInicioContrato, DataFimContrato, NumHoraSemanal, SalarioHora)
VALUES (1, 1, '2025-01-01', '2025-12-31', 40, 150.00);

INSERT INTO Contrato (IDPessoa, IDPerfil, DataInicioContrato, DataFimContrato, NumHoraSemanal, SalarioHora)
VALUES (2, 2, '2025-01-01', '2025-12-31', 40, 100.00);

INSERT INTO Contrato (IDPessoa, IDPerfil, DataInicioContrato, DataFimContrato, NumHoraSemanal, SalarioHora)
VALUES (3, 2, '2025-01-01', '2025-12-31', 30, 90.00);

INSERT INTO Contrato (IDPessoa, IDPerfil, DataInicioContrato, DataFimContrato, NumHoraSemanal, SalarioHora)
VALUES (4, 3, '2025-01-01', '2025-12-31', 35, 95.00);

INSERT INTO Contrato (IDPessoa, IDPerfil, DataInicioContrato, DataFimContrato, NumHoraSemanal, SalarioHora)
VALUES (5, 4, '2025-01-01', '2025-12-31', 20, 120.00);

-- Projetos
INSERT INTO Projeto (Nome, DataInicioProj, DataFimProj, Descricao)
VALUES ('Plataforma Financeira', '2025-02-01', NULL, 'Sistema para controle de investimentos.');

INSERT INTO Projeto (Nome, DataInicioProj, DataFimProj, Descricao)
VALUES ('App Mobile Vendas', '2025-03-01', NULL, 'Aplicativo de vendas online.');

-- Alocações
-- Projeto 1
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (1, 1, 20); -- Gerente
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (1, 2, 25); -- Dev
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (1, 3, 15); -- Dev
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (1, 4, 20); -- QA
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (1, 5, 10); -- Security

-- Projeto 2
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (2, 1, 15); -- Gerente
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (2, 2, 20); -- Dev
INSERT INTO Alocacao (IDProjeto, IDContrato, horaSemana) VALUES (2, 4, 15); -- QA
