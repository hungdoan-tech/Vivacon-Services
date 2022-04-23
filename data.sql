INSERT INTO "role" ("id", "name")
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO "account" ("id", "full_name", "password", "refresh_token", "token_expired_date", "username", "role_id",
                       "created_at", "created_by_account_id", "last_modified_at", "last_modified_by_account_id", "bio",
                       "email", "verification_token", verification_expired_date, "active")
VALUES (1, 'Hung Doan', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi',
        'd68b5f73-d337-4959-8316-f6fdf603b36c', '2022-04-07 03:01:52.457204', 'hungdoan', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, NULL, 'hungdoan@gmail.com', NULL, NULL, true),
       (2, 'Thao Van', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'thaovan', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, NULL, 'thaovan@gmail.com', NULL, NULL, true),
       (3, 'My Han', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'myhan', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, NULL, 'myhan@gmail.com', NULL, NULL, true),
       (4, 'Thanh Thuy', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'thanhthuy', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, NULL, 'thanhthuy@gmail.com', NULL, NULL, true),
       (5, 'Truc Van', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'trucvan', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, NULL, 'trucvan@gmail.com', NULL, NULL, true),
       (6, 'Xuan Thuy', '$2a$10$9y6WAausHYtvwMUOHj9qQuLQTgaZn.Bz04w2EG6pSAn1w9wvUtPXi', NULL, NULL, 'xuanthuy', 2,
        '2022-04-01 23:56:20.061855', NULL, NULL, NULL, NULL, 'xuanthuy@gmail.com', NULL, NULL, true);
SELECT setval('account_id_seq', (SELECT MAX(id) FROM account) + 1);

INSERT INTO "post" ("id", "created_at", "last_modified_at", "caption", "privacy", "created_by_account_id",
                    "last_modified_by_account_id", "active")
VALUES (1, '2022-04-01 23:56:20.061855', '2022-04-01 23:56:20.061855', 'Hello this is caption', 1, 1, NULL, true),
       (2, '2022-04-02 00:02:45.371366', '2022-04-01 23:56:20.061855', 'Hello this is caption', 1, 2, NULL, true),
       (3, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 2, NULL, true),
       (4, '2022-04-02 00:26:11.900023', '2022-04-01 23:56:20.061855', 'Hello this is caption', 0, 1, NULL, true),
       (5, '2022-04-02 00:13:12.155683', '2022-04-01 23:56:20.061855', 'Hello this is caption', 1, 2, NULL, true),
       (6, '2022-04-01 23:56:43.648472', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 2, NULL, true),
       (7, '2022-04-01 23:52:38.311749', '2022-04-01 23:56:20.061855', 'Hello this is caption', 0, 3, NULL, true),
       (8, '2022-04-02 00:02:45.371366', '2022-04-01 23:56:20.061855', 'Hello this is caption', 1, 4, NULL, true),
       (9, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 5, NULL, true),
       (10, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (11, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (12, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (13, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (14, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (15, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (16, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (17, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (18, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (19, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (20, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (21, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (22, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (23, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (24, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (25, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (26, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true),
       (27, '2022-04-02 00:08:00.991663', '2022-04-01 23:56:20.061855', 'Hello this is caption', 2, 1, NULL, true);


SELECT setval('post_id_seq', (SELECT MAX(id) FROM post) + 1);

INSERT INTO "following" ("id", "from_account", "to_account")
VALUES (1, 1, 2),
       (2, 1, 3),
       (3, 1, 5),
       (4, 2, 1),
       (5, 4, 1),
       (6, 6, 1);
SELECT setval('following_id_seq', (SELECT MAX(id) FROM FOLLOWING) + 1);

INSERT INTO "attachment" ("id", "actual_name", "unique_name", "url", "timestamp", "post_id", "profile_id")
VALUES (3, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 1, 1),
       (4, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 2, 1),
       (5, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 3, NULL),
       (6, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 3, NULL),
       (7, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 4, NULL),
       (8, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 4, NULL),
       (9, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 5, NULL),
       (10, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 5, NULL),
       (11, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 6, NULL),
       (12, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 6, NULL),
       (13, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 7, NULL),
       (14, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 7, NULL),
       (17, '272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-01T22:15:53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.303388500_272205323_4927332657326798_3776136439423965886_n.jpg',
        '2022-04-02 00:08:00.991663', 9, NULL),
       (18, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 10, NULL),
       (19, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 11, NULL),
       (20, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 12, NULL),
       (21, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 13, NULL),
       (22, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 14, NULL),
       (23, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 15, NULL),
       (24, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 16, NULL),
       (25, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 17, NULL),
       (26, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 18, NULL),
       (27, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 19, NULL),
       (28, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 20, NULL),
       (29, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 21, NULL),
       (30, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 22, NULL),
       (31, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 23, NULL),
       (32, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 24, NULL),
       (33, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 25, NULL),
       (34, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 26, NULL),
       (35, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 27, NULL),
       (36, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 17, NULL),
       (37, 'spring-boot-authentication-spring-security-architecture.png',
        '2022-04-01T22:15:53.593384100_spring-boot-authentication-spring-security-architecture.png',
        'https://vivacon-objects.s3-ap-southeast-1.amazonaws.com/2022-04-01T22%3A15%3A53.593384100_spring-boot-authentication-spring-security-architecture.png',
        '2022-04-02 00:08:00.991663', 27, NULL);
SELECT setval('attachment_id_seq', (SELECT MAX(id) FROM attachment) + 1);

INSERT INTO "comment" ("id", "created_at", "last_modified_at", "content", "parent_comment_id", "post_id",
                       "created_by_account_id",
                       "last_modified_by_account_id", "active")
VALUES (1, '2022-04-01 23:56:20.061855', NULL, 'Hello this is comment 1', NULL, 2, 1, NULL, true),
       (2, '2022-04-02 23:56:20.061855', NULL, 'Hello this is parent comment 2', NULL, 2, 1, NULL, true),
       (3, '2022-04-03 23:56:20.061855', NULL, 'Hello this is child comment 3', 2, 2, 1, NULL, true),
       (4, '2022-04-04 23:56:20.061855', NULL, 'Hello this is child comment 4', 2, 2, 1, NULL, true),
       (5, '2022-04-05 23:56:20.061855', NULL, 'Hello this is parent comment 5', 4, 3, 1, NULL, true),
       (6, '2022-04-06 23:56:20.061855', NULL, 'Hello this is comment 6', 5, 3, 1, NULL, true),
       (7, '2022-04-07 23:56:20.061855', NULL, 'Hello this is comment 7', 6, 3, 1, NULL, true),
       (8, '2022-04-08 23:56:20.061855', NULL, 'Hello this is comment 8', 6, 3, 1, NULL, true),
       (9, '2022-04-09 23:56:20.061855', NULL, 'Hello this is parent comment 9', 4, 4, 1, NULL, true),
       (10, '2022-04-10 23:56:20.061855', NULL, 'Hello this is child comment 10', 9, 4, 1, NULL, true);
SELECT setval('comment_id_seq', (SELECT MAX(id) FROM comment) + 1);

INSERT INTO "liking" ("id", "account_id", "post_id")
VALUES (1, 2, 2),
       (2, 1, 2),
       (3, 1, 3);
SELECT setval('like_id_seq', (SELECT MAX(id) FROM liking) + 1);
