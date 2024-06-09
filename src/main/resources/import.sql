INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_category(name) VALUES ('Front-End');
INSERT INTO tb_category(name) VALUES ('Back-End');
INSERT INTO tb_category(name) VALUES ('Full-Stack');

INSERT INTO tb_knowledge(title, text, archive) VALUES ('HTML', '', '');
INSERT INTO tb_knowledge(title, text, archive) VALUES ('CSS', '', '');
INSERT INTO tb_knowledge(title, text, archive) VALUES ('Spring Boot', '', '');

INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (1, 1);
INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (2, 1);
INSERT INTO tb_knowledge_category(knowledge_id, category_id) VALUES (3, 2);