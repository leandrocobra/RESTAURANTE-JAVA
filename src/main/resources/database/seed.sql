INSERT INTO mesas (numero, status)
VALUES (1, 'LIVRE'),
       (2, 'LIVRE'),
       (3, 'LIVRE'),
       (4, 'LIVRE'),
       (5, 'LIVRE');

INSERT INTO usuarios (usuario, senha, perfil, ativo)
VALUES ('admin', '123', 'ADM', TRUE),
       ('garcom', '123', 'GARCOM', TRUE),
       ('cozinha', '123', 'COZINHA', TRUE),
       ('caixa', '123', 'CAIXA', TRUE);

INSERT INTO produtos (nome, preco, ativo)
VALUES ('Baguncinha', 18.00, TRUE),
       ('X-Burger', 20.00, TRUE),
       ('X-Salada', 22.00, TRUE),
       ('Porção de batata', 15.00, TRUE),
       ('Coca-Cola 1L', 10.00, TRUE),
       ('Água 500ml', 4.00, TRUE),
       ('Suco de Laranja 300ml', 7.00, TRUE),
       ('Guaraná Lata 350ml', 6.00, TRUE);