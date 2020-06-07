--RESERVATION-STATE-----------------------------------
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (1, 'PENDING_APPROVAL');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (2, 'RESERVED');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (3, 'CANCELED');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (4, 'RESERVATION_PAID');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (5, 'PENDING_PENALTY_PAYMENT');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (6, 'COMPLETED');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (7, 'CART');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (8, 'DENIED');

--PUBLISHER-TYPE------------------------------------
INSERT INTO PUBLISHER_TYPE (ID, NAME) VALUES (1, 'BASIC');
INSERT INTO PUBLISHER_TYPE (ID, NAME) VALUES (2, 'AGENT');

--PRICE-LIST-----------------------------------------------
INSERT INTO PRICE_LIST (ID, NAME, DISCOUNT_PERCENTAGE, MILEAGE_THRESHOLD, MILEAGE_PENALTY, WARRANTY_PRICE, PUBLISHER_ID, PUBLISHER_TYPE_ID)
VALUES (1, 'Car 1 Price list', 20, 200, 20, 100, 1, 2 );

INSERT INTO PRICE_LIST (ID, NAME, DISCOUNT_PERCENTAGE, MILEAGE_THRESHOLD, MILEAGE_PENALTY, WARRANTY_PRICE, PUBLISHER_ID, PUBLISHER_TYPE_ID)
VALUES (2, 'Car 2 Price list', 15, 200, 20, null, 1, 2 );

INSERT INTO PRICE_LIST (ID, NAME, DISCOUNT_PERCENTAGE, MILEAGE_THRESHOLD, MILEAGE_PENALTY, WARRANTY_PRICE, PUBLISHER_ID, PUBLISHER_TYPE_ID)
VALUES (3, 'Car 3 Price list', 5, null, null, 200, 1, 2 );

INSERT INTO PRICE_LIST (ID, NAME, DISCOUNT_PERCENTAGE, MILEAGE_THRESHOLD, MILEAGE_PENALTY, WARRANTY_PRICE, PUBLISHER_ID, PUBLISHER_TYPE_ID)
VALUES (4, 'Car 4 Price list', 3, null, null, null, 1, 2 );

INSERT INTO PRICE_LIST (ID, NAME, DISCOUNT_PERCENTAGE, MILEAGE_THRESHOLD, MILEAGE_PENALTY, WARRANTY_PRICE, PUBLISHER_ID, PUBLISHER_TYPE_ID)
VALUES (5, 'Car 5 Price list', 10, 200, 20, null, 1, 1 );

--CAR-PRICE-LIST------------------------------------------
INSERT INTO CAR_PRICE_LIST (ID, CAR_ID, PRICE_LIST_ID, UNIX_TIMESTAMP)
VALUES (1, 1, 1, 1546300800);

INSERT INTO CAR_PRICE_LIST (ID, CAR_ID, PRICE_LIST_ID, UNIX_TIMESTAMP)
VALUES (2, 2, 2, 1546300800);

INSERT INTO CAR_PRICE_LIST (ID, CAR_ID, PRICE_LIST_ID, UNIX_TIMESTAMP)
VALUES (3, 3, 3, 1546300800);

INSERT INTO CAR_PRICE_LIST (ID, CAR_ID, PRICE_LIST_ID, UNIX_TIMESTAMP)
VALUES (4, 4, 4, 1546300800);

INSERT INTO CAR_PRICE_LIST (ID, CAR_ID, PRICE_LIST_ID, UNIX_TIMESTAMP)
VALUES (5, 5, 5, 1546300800);

--PRICE-----------------------------------------------
INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (1, 1, PARSEDATETIME('1 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('1 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 15);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (2, 1, PARSEDATETIME('2 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('2 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 15);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (3, 1, PARSEDATETIME('3 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'), PARSEDATETIME('3 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 15);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (4, 2, PARSEDATETIME('1 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('1 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 20);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (5, 2, PARSEDATETIME('2 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('2 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 20);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (6, 2, PARSEDATETIME('3 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'), PARSEDATETIME('3 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 20);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (7, 3, PARSEDATETIME('1 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('1 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 25);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (8, 3, PARSEDATETIME('2 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('2 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 25);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (9, 3, PARSEDATETIME('3 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'), PARSEDATETIME('3 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 25);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (10, 4, PARSEDATETIME('1 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('1 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 30);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (11, 4, PARSEDATETIME('2 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('2 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 30);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (12, 4, PARSEDATETIME('3 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'), PARSEDATETIME('3 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 30);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (13, 5, PARSEDATETIME('1 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('1 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 35);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (14, 5, PARSEDATETIME('2 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'),PARSEDATETIME('2 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 35);

INSERT INTO PRICE (ID, PRICE_LIST_ID, START_DATE, END_DATE, PRICE)
VALUES (15, 5, PARSEDATETIME('3 Sep 2020 08:00:00','dd MMM yyyy HH:mm:ss','en'), PARSEDATETIME('3 Sep 2020 23:00:00','dd MMM yyyy HH:mm:ss','en'), 35);

--BUNDLE--------------------------------------------
INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID, TOTAL_PRICE, EXTRA_CHARGES)
VALUES (1, 1, 2, 2, 6, 2500, 300);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID, TOTAL_PRICE, EXTRA_CHARGES)
VALUES (2, 1, 2, 2, 4, 2500, 300);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID, TOTAL_PRICE, EXTRA_CHARGES)
VALUES (3, 1, 5, 2, 1, 2500, 300);


--RESERVATION----------------------------------------
INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (1, 1, 1, 5, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (2, 1, 1, 2, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (3, 1, 1, 1, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (4, 1, 1, 4, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

------------

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (5, 1, 2, 5, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (20, 2, 2, 5, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));


INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (6, 1, 2, 5, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (7, 1, 2, 5, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (8, 1, 2, 4, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

--------------

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (9, 1, 3, 1, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (10, 1, 3, 1, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (11, 1, 3, 1, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (12, 1, 3, 1, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

--------------

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (13, 1, 4, 4, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (14, 1, 4, 4, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (15, 1, 4, 5, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE)
VALUES (16, 1, 4, 4, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'));

--COMMENT-------------------------------
INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (1, 1, 1, 1, TRUE, '1589927112', 'Beautiful car, great service');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (2, 1, 2, 1, TRUE, '1589927112', 'Thank you, we hope to see you again soon!');

---

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (3, 1, 1, 2, TRUE, '1589927112', 'The car was very dirty and there was a rattling noise coming from the engine.');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (4, 1, 2, 2, TRUE, '1589927112', 'We are sorry to hear you had a bad experience. All cars are thoroughly cleaned. We also consider all complaints very seriously. We hope to see you again!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (5, 1, 1, 3, TRUE, '1589927112', 'Never again!!');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (6, 1, 2, 3, TRUE, '1589927112', 'Dear customer, we are sorry you had a bad experience. We still hope to remedy any complaints you may have, and hope to serve you in the future.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (7, 1, 1, 4, TRUE, '1589927112', 'You get what you pay for');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (8, 1, 2, 4, TRUE, '1589927112', 'Dear user, thank you for your comment and rating. We try to keep our services at low cost. The cars are maintained after every use.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (9, 1, 1, 5, TRUE, '1589927112', 'Will reserve again');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (10, 1, 2, 5, TRUE, '1589927112', 'Dear customer, we are happy to hear you are satisfied with our services. Thank you, we hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (11, 1, 1, 6, TRUE, '1589927112', 'Such a great car');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (12, 1, 2, 6, TRUE, '1589927112', 'Thank you for your comment and rating. We are happy to hear you are satisfied with our car and services. We hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (13, 1, 1, 7, TRUE, '1589927112', 'I am 76 years old and just returned this car after reserving it for 5 days. Owned many cars in my lifetime,Corvett, Land rover,fords and on and on , cannot compare to this car. ');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (14, 1, 2, 7, TRUE, '1589927112', 'Thank you for your rating and comment, we hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (15, 1, 1, 8, TRUE, '1589927112', 'I gave 4 stars because of the price');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (16, 1, 2, 8, TRUE, '1589927112', 'Dear customer, thank you for your reply and rating. Our prices are as low as possible, while still maintaining good services.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (17, 1, 1, 9, TRUE, '1589927112', 'Just no.');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (18, 1, 2, 9, TRUE, '1589927112', 'We are sorry you are not satisfied with our services. We hope to see you again and resolve any issues that you may have had.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (19, 1, 1, 10, TRUE, '1589927112', 'Dirty, old, rusted, do not want to be caught in it ever again');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (20, 1, 2, 10, TRUE, '1589927112', 'Dear customer, we are sorry to hear you had a bad experience. We hope to see you again and resolve any issues that you may have had.');
--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (21, 1, 1, 11, TRUE, '1589927112', 'It looks nothing like in the picture. Someone crashed in the door and it does not close well. Had to slam the door at every cross-road');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (22, 1, 1, 13, TRUE, '1589927112','You get what you pay for, it is worth the money but it is not in mint condition');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (23, 1, 2, 13, TRUE, '1589927112','Dear user, thank you for your comment and rating. We try to keep our services at low cost. The cars are maintained after every use.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (24, 1, 1, 14, TRUE, '1589927112','There was a weird smell, otherwise ok');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (25, 1, 2, 14, TRUE, '1589927112','Dear customer, we are sorry to hear you had a bad experience. We hope to see you again and resolve any other issues you may have had.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (26, 1, 1, 15, TRUE, '1589927112','I rented this car for my sons prom, he was very happy');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (27, 1, 2, 15, TRUE, '1589927112','Thank you, we hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (28, 1, 1, 16, TRUE, '1589927112','It would not start so I was late, but it was ok after that');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (29, 1, 2, 16, TRUE, '1589927112','Thank you for your rating and comment, we hope we managed to resolve your issues in time. Hope to see you again soon!');
