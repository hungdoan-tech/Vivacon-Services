INSERT INTO "role" ("id", "name")
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO "account" ("id", "full_name", "password", "refresh_token", "token_expired_date", "username", "role_id",
                       "created_at", "created_by_account_id", "last_modified_at", "last_modified_by_account_id",
                       "active")
VALUES (1, 'Hung Doan', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi',
        'd68b5f73-d337-4959-8316-f6fdf603b36c', '2022-04-07 03:01:52.457204', 'hungdoan', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, true),
       (2, 'Thao Van', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'thaovan', 2,
        '2022-04-01 23:56:20.061855', 1, NULL, NULL, true),
       (3, 'My Han', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'myhan', 2,
        '2022-04-01 23:56:20.061855', 1, NULL, NULL, true),
       (4, 'Thanh Thuy', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'thanhthuy', 2,
        '2022-04-01 23:56:20.061855', 1, NULL, NULL, true),
       (5, 'Truc Van', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'trucvan', 2,
        '2022-04-01 23:56:20.061855', 1, NULL, NULL, true),
       (6, 'Xuan Thuy', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'xuanthuy', 2,
        '2022-04-01 23:56:20.061855', 1, NULL, NULL, true);
SELECT setval('account_id_seq', (SELECT MAX(id) FROM account) + 1);

INSERT INTO "post" ("id", "created_at", "last_modified_at", "caption", "privacy", "created_by_account_id",
                    "last_modified_by_account_id", "active")
VALUES (1, '2022-04-01 23:56:20.061855', NULL, 'Hello this is caption', 1, 1, NULL, true),
       (2, '2022-04-02 00:02:45.371366', NULL, 'Hello this is caption', 1, 2, NULL, true),
       (3, '2022-04-02 00:08:00.991663', NULL, 'Hello this is caption', 2, 2, NULL, true),
       (4, '2022-04-02 00:26:11.900023', NULL, 'Hello this is caption', 0, 1, NULL, true),
       (5, '2022-04-02 00:13:12.155683', NULL, 'Hello this is caption', 1, 2, NULL, true),
       (6, '2022-04-01 23:56:43.648472', NULL, 'Hello this is caption', 2, 2, NULL, true),
       (7, '2022-04-01 23:52:38.311749', NULL, 'Hello this is caption', 0, 3, NULL, true),
       (8, '2022-04-02 00:02:45.371366', NULL, 'Hello this is caption', 1, 4, NULL, true),
       (9, '2022-04-02 00:08:00.991663', NULL, 'Hello this is caption', 2, 5, NULL, true),
       (10, '2022-04-02 00:26:11.900023', NULL, 'Hello this is caption', 0, 6, NULL, true),
       (11, '2022-04-02 00:13:12.155683', NULL, 'Hello this is caption', 1, 4, NULL, true),
       (12, '2022-04-01 23:56:43.648472', NULL, 'Hello this is caption', 2, 3, NULL, true),
       (13, '2022-04-01 23:52:38.311749', NULL, 'Hello this is caption', 0, 4, NULL, true),
       (14, '2022-04-02 00:08:00.991663', NULL, 'Hello this is caption', 2, 5, NULL, true),
       (15, '2022-04-02 00:26:11.900023', NULL, 'Hello this is caption', 0, 6, NULL, true),
       (16, '2022-04-02 00:13:12.155683', NULL, 'Hello this is caption', 1, 6, NULL, true),
       (17, '2022-04-01 23:56:43.648472', NULL, 'Hello this is caption', 2, 5, NULL, true),
       (18, '2022-04-01 23:52:38.311749', NULL, 'Hello this is caption', 0, 4, NULL, true);
SELECT setval('post_id_seq', (SELECT MAX(id) FROM post) + 1);

INSERT INTO "following" ("id", "from_account", "to_account")
VALUES (1, 1, 2),
       (2, 1, 3),
       (3, 1, 5),
       (4, 2, 1),
       (5, 4, 1),
       (6, 6, 1);
SELECT setval('following_id_seq', (SELECT MAX(id) FROM FOLLOWING) + 1);

INSERT INTO "attachment" ("id", "actual_name", "unique_name", "url", "innovation_id")
VALUES (3, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        2),
       (4, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        2),
       (5, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        3),
       (6, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        3),
       (7, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        4),
       (8, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        4),
       (9, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        5),
       (10, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        5),
       (11, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        6),
       (12, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        6),
       (13, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        7),
       (14, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        7),
       (17, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        9),
       (18, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        9);
SELECT setval('attachment_id_seq', (SELECT MAX(id) FROM attachment) + 1);

INSERT INTO "comment" ("id", "created_at", "last_modified_at", "content", "parent_comment_id", "post_id", "created_by_account_id",
                    "last_modified_by_account_id", "active")
VALUES (1, '2022-04-01 23:56:20.061855', NULL, 'Hello this is comment', NULL, 2, 1, NULL, true);
SELECT setval('comment_id_seq', (SELECT MAX(id) FROM comment) + 1);
















