--STATUS-----------------------
MERGE INTO STATUS AS target USING (SELECT 1 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (1,'PENDING_APPROVAL');

MERGE INTO STATUS AS target USING (SELECT 2 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (2,'ACTIVATED');

MERGE INTO STATUS AS target USING (SELECT 3 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (3,'BLOCKED');

MERGE INTO STATUS AS target USING (SELECT 4 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (4,'DELETED');

--ROLE-------------------------
MERGE INTO ROLE AS target USING (SELECT 1 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (1,'BASIC');

MERGE INTO ROLE AS target USING (SELECT 2 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (2,'AGENT');

MERGE INTO ROLE AS target USING (SELECT 3 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (3,'ADMIN');

--RESOURSE TYPE----------------
MERGE INTO RESOURSE_TYPE AS target USING (SELECT 1 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (1,'USER');

MERGE INTO RESOURSE_TYPE AS target USING (SELECT 2 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (2,'AGENT');

--PERMISSION-------------------
MERGE INTO PERMISSION AS target USING (SELECT 1 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (1, 'Add Car');

MERGE INTO PERMISSION AS target USING (SELECT 2 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (2, 'Add Car Image');

MERGE INTO PERMISSION AS target USING (SELECT 3 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (3, 'Add Car Unavailability');

MERGE INTO PERMISSION AS target USING (SELECT 4 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (4, 'Manage prices');

MERGE INTO PERMISSION AS target USING (SELECT 5 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (5, 'Manage reservations');

MERGE INTO PERMISSION AS target USING (SELECT 6 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (6, 'Chat');

MERGE INTO PERMISSION AS target USING (SELECT 7 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, NAME) VALUES (7, 'Add car report');

--USER---------------------------
MERGE INTO USER AS target USING (SELECT 1 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, ROLE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, STATUS_ID, EMAIL_VERIFIED, PASSWORD) VALUES (1, 1, 'StudentFirstName', 'StudentLastName', 'student@mail.com', '021000001', 2, TRUE, '$2a$10$iIlRkT3uRcprLxxzsagQzuiV6WCwN9Cyuu1gUCp7ACmIPzv5WxQHa');

MERGE INTO USER AS target USING (SELECT 2 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, ROLE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, STATUS_ID, EMAIL_VERIFIED, PASSWORD) VALUES (2, 2, 'AgentFirstName', 'AgentLastName', 'agent@mail.com', '021000002', 2, TRUE, '$2a$10$iIlRkT3uRcprLxxzsagQzuiV6WCwN9Cyuu1gUCp7ACmIPzv5WxQHa');

MERGE INTO USER AS target USING (SELECT 3 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, ROLE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, STATUS_ID, EMAIL_VERIFIED, PASSWORD) VALUES (3, 3, 'AdminFirstName', 'AdminLastName', 'admin@mail.com', '021000003', 2, TRUE, '$2a$10$iIlRkT3uRcprLxxzsagQzuiV6WCwN9Cyuu1gUCp7ACmIPzv5WxQHa');

--USER PERMISSION--------
-- ADD CAR
MERGE INTO USER_PERMISSION AS target USING (SELECT 1 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (1, 1, 1, '1589665206', 1, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 2 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (2, 2, 1, '1589665206', 2, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 3 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (3, 3, 1, '1589665206', null, null);

-- ADD CAR IMAGE
MERGE INTO USER_PERMISSION AS target USING (SELECT 4 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (4, 1, 2, '1589665206', 1, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 5 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (5, 2, 2, '1589665206', 2, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 6 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (6, 3, 2, '1589665206', null, null);

-- ADD CAR UNAVAILABILITY

MERGE INTO USER_PERMISSION AS target USING (SELECT 7 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (7, 2, 3, '1589665206', 2, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 8 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (8, 3, 3, '1589665206', 2, null);

-- ADD PRICE
MERGE INTO USER_PERMISSION AS target USING (SELECT 9 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (9, 1, 4, '1589665206', 1, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 10 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (10, 2, 4, '1589665206', 2, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 11 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (11, 3, 4, '1589665206', null, null);

--

MERGE INTO USER_PERMISSION AS target USING (SELECT 12 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (12, 1, 5, '1589665206', 1, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 13 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (13, 2, 5, '1589665206', 2, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 14 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (14, 3, 5, '1589665206', null, null);

--

MERGE INTO USER_PERMISSION AS target USING (SELECT 15 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (15, 1, 6, '1589665206', 1, 1);

MERGE INTO USER_PERMISSION AS target USING (SELECT 16 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (16, 2, 6, '1589665206', 2, 1);

--

MERGE INTO USER_PERMISSION AS target USING (SELECT 17 as ID) AS source ON target.ID=source.ID
WHEN NOT MATCHED THEN INSERT (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID) VALUES (17, 2, 7, '1589665206', 2, 1);