INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_domain(name, active) VALUES ('Desenvolvimento de Software', true);
INSERT INTO tb_domain(name, active) VALUES ('Testes de Software', true);
INSERT INTO tb_domain(name, active) VALUES ('DevOps', true);
INSERT INTO tb_domain(name, active) VALUES ('Data Sciente', true);
INSERT INTO tb_domain(name, active) VALUES ('Gestão', true);

INSERT INTO tb_category(name) VALUES ('Front-End');
INSERT INTO tb_category(name) VALUES ('Back-End');
INSERT INTO tb_category(name) VALUES ('Testes de Unidade');
INSERT INTO tb_category(name) VALUES ('Automação de Testes');
INSERT INTO tb_category(name) VALUES ('Big Data Analytics');
INSERT INTO tb_category(name) VALUES ('Infraestrutura como Código (IaC)');
INSERT INTO tb_category(name) VALUES ('Testes Funcionais');
INSERT INTO tb_category(name) VALUES ('Análise Exploratória de Dados');

INSERT INTO tb_category_domain(category_id, domain_id) VALUES (1, 1);
INSERT INTO tb_category_domain(category_id, domain_id) VALUES (2, 1);
INSERT INTO tb_category_domain(category_id, domain_id) VALUES (3, 2);
INSERT INTO tb_category_domain(category_id, domain_id) VALUES (4, 2);
INSERT INTO tb_category_domain(category_id, domain_id) VALUES (5, 4);
INSERT INTO tb_category_domain(category_id, domain_id) VALUES (6, 3);

INSERT INTO tb_knowledge(title, text, archive) VALUES ('JavaScript e React', '', '');
INSERT INTO tb_knowledge(title, text, archive) VALUES ('Java Spring Boot', '', '');
INSERT INTO tb_knowledge(title, text, archive) VALUES ('JUnit (Java)', '', '');
INSERT INTO tb_knowledge(title, text, archive) VALUES ('Cucumber (BDD)', '', '');
INSERT INTO tb_knowledge(title, text, archive) VALUES ('Pandas (Python)', '', '');

INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (1, 1);
INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (2, 2);
INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (3, 3);
INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (4, 7);
INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (5, 8);