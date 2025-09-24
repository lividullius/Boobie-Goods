-- ======================================
-- Inserts de Dados
-- ======================================

-- Perfis
INSERT INTO Perfil (Tipo) VALUES ('Gerente');
INSERT INTO Perfil (Tipo) VALUES ('Developer');
INSERT INTO Perfil (Tipo) VALUES ('QualityAnalyst');
INSERT INTO Perfil (Tipo) VALUES ('Security');

-- Pessoas (sem IDPerfil - agora é N:N)
INSERT INTO Pessoa (Nome) VALUES ('Ana Souza');
INSERT INTO Pessoa (Nome) VALUES ('Carlos Lima');
INSERT INTO Pessoa (Nome) VALUES ('Fernanda Alves');
INSERT INTO Pessoa (Nome) VALUES ('Rafael Costa');
INSERT INTO Pessoa (Nome) VALUES ('Juliana Torres');

-- Relacionamento N:N Pessoa-Perfil
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (1, 1); -- Ana: Gerente
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (2, 2); -- Carlos: Developer
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (3, 2); -- Fernanda: Developer
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (4, 3); -- Rafael: QA
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (5, 4); -- Juliana: Security
-- Exemplo de pessoa com múltiplos perfis
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (2, 4); -- Carlos: Developer + Security
INSERT INTO PessoaPerfil (IDPessoa, IDPerfil) VALUES (4, 2); -- Rafael: QA + Developer

-- Contratos
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

-- Alocações (agora com IDPessoa)
-- Projeto 1
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (1, 1, 1, 20); -- Ana Gerente
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (1, 2, 2, 25); -- Carlos Dev
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (1, 3, 3, 15); -- Fernanda Dev
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (1, 4, 4, 20); -- Rafael QA
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (1, 5, 5, 10); -- Juliana Security

-- Projeto 2
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (2, 1, 1, 15); -- Ana Gerente
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (2, 2, 2, 20); -- Carlos Dev
INSERT INTO Alocacao (IDProjeto, IDContrato, IDPessoa, horaSemana) VALUES (2, 4, 4, 15); -- Rafael QA
