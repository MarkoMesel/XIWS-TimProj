--CARSERVICE---------------------------------------------

--LOCATION
INSERT INTO LOCATION ( ID, NAME) VALUES (1, 'Beograd');
INSERT INTO LOCATION ( ID, NAME) VALUES (2, 'Novi Sad');
INSERT INTO LOCATION ( ID, NAME) VALUES (3, 'Niš');
INSERT INTO LOCATION ( ID, NAME) VALUES (4, 'Priština');
INSERT INTO LOCATION ( ID, NAME) VALUES (5, 'Kragujevac');
INSERT INTO LOCATION ( ID, NAME) VALUES (6, 'Leskovac');
INSERT INTO LOCATION ( ID, NAME) VALUES (7, 'Subotica');
INSERT INTO LOCATION ( ID, NAME) VALUES (8, 'Zrenjanin');
INSERT INTO LOCATION ( ID, NAME) VALUES (9, 'Pančevo');
INSERT INTO LOCATION ( ID, NAME) VALUES (10, 'Čačak');
INSERT INTO LOCATION ( ID, NAME) VALUES (11, 'Novi Pazar');
INSERT INTO LOCATION ( ID, NAME) VALUES (12, 'Kraljevo');
INSERT INTO LOCATION ( ID, NAME) VALUES (13, 'Smederevo');
INSERT INTO LOCATION ( ID, NAME) VALUES (14, 'Valjevo');
INSERT INTO LOCATION ( ID, NAME) VALUES (15, 'Kruševac');
INSERT INTO LOCATION ( ID, NAME) VALUES (16, 'Vranje');
INSERT INTO LOCATION ( ID, NAME) VALUES (17, 'Šabac');
INSERT INTO LOCATION ( ID, NAME) VALUES (18, 'Užice');
INSERT INTO LOCATION ( ID, NAME) VALUES (19, 'Sombor');
INSERT INTO LOCATION ( ID, NAME) VALUES (20, 'Požarevac');

--PUBLISHER TYPE
INSERT INTO CAR_PUBLISHER_TYPE ( ID, NAME) VALUES (1, 'USER');
INSERT INTO CAR_PUBLISHER_TYPE ( ID, NAME) VALUES (2, 'AGENT');

--CAR CLASS
INSERT INTO CAR_CLASS ( ID, NAME) VALUES (1, 'Limousine');
INSERT INTO CAR_CLASS ( ID, NAME) VALUES (2, 'Hatchback');
INSERT INTO CAR_CLASS ( ID, NAME) VALUES (3, 'Coupe');
INSERT INTO CAR_CLASS ( ID, NAME) VALUES (4, 'Convertible');
INSERT INTO CAR_CLASS ( ID, NAME) VALUES (5, 'Kombi');

--TRANSMISSION TYPE
INSERT INTO TRANSMISSION_TYPE ( ID, NAME) VALUES (1, 'Manual');
INSERT INTO TRANSMISSION_TYPE ( ID, NAME) VALUES (2, 'Automatic');
INSERT INTO TRANSMISSION_TYPE ( ID, NAME) VALUES (3, 'CVT');

--FUEL TYPE
INSERT INTO FUEL_TYPE ( ID, NAME) VALUES (1, 'Petrol');
INSERT INTO FUEL_TYPE ( ID, NAME) VALUES (2, 'Diesel');
INSERT INTO FUEL_TYPE ( ID, NAME) VALUES (3, 'CNG');
INSERT INTO FUEL_TYPE ( ID, NAME) VALUES (4, 'Hybrid');
INSERT INTO FUEL_TYPE ( ID, NAME) VALUES (5, 'Electric');

--CAR MANUFACTURER 
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (1, 'Honda');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (2, 'Opel');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (3, 'Ford');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (4, 'Fiat');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (5, 'Tesla');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (6, 'BMW');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (7, 'Audi');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (8, 'Subaru');
INSERT INTO CAR_MANUFACTURER ( ID, NAME) VALUES (9, 'Mazda');

--CAR MODEL
--Honda
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (1, 1, 'Civic');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (2, 1, 'Accord');
--Opel
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (3, 2, 'Astra G');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (4, 2, 'Insignia');
--Ford
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (5, 3, 'Focus');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (6, 3, 'Fiesta');
--Fiat
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (7, 4, 'Panda');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (8, 4, 'Stilo');
--Tesla
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (9, 5, 'Model 3');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (10, 5, 'Model S');
--BMW
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (11, 6, 'I8');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (12, 6, 'X7');
--Audi
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (13, 7, 'A6');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (14, 7, 'Q5');
--Subaru
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (15, 8, 'Impreza');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (16, 8, 'Forester');
--Mazda
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (17, 9, 'CX-5');
INSERT INTO CAR_MODEL ( ID, MANUFACTURER_ID, NAME) VALUES (18, 9, 'CX-9');

--CAR
--Honda
INSERT INTO CAR ( ID, ACTIVE, PUBLISHER_ID, PUBLISHER_TYPE_ID, CHILD_SEATS, MILEAGE, CARCLASS_ID, MODEL_ID, FUELTYPE_ID, TRANSMISSIONTYPE_ID, LOCATION_ID) 
VALUES (1, TRUE, 1, 2, 1, 225000, 3, 1, 1, 1, 1);
INSERT INTO CAR ( ID, ACTIVE, PUBLISHER_ID, PUBLISHER_TYPE_ID, CHILD_SEATS, MILEAGE, CARCLASS_ID, MODEL_ID, FUELTYPE_ID, TRANSMISSIONTYPE_ID, LOCATION_ID) 
VALUES (2, TRUE, 1, 2, 0, 110000, 1, 1, 2, 2, 1);
INSERT INTO CAR ( ID, ACTIVE, PUBLISHER_ID, PUBLISHER_TYPE_ID, CHILD_SEATS, MILEAGE, CARCLASS_ID, MODEL_ID, FUELTYPE_ID, TRANSMISSIONTYPE_ID, LOCATION_ID) 
VALUES (3, TRUE, 1, 2, 1, 219000, 2, 2, 3, 1, 1);
INSERT INTO CAR ( ID, ACTIVE, PUBLISHER_ID, PUBLISHER_TYPE_ID, CHILD_SEATS, MILEAGE, CARCLASS_ID, MODEL_ID, FUELTYPE_ID, TRANSMISSIONTYPE_ID, LOCATION_ID) 
VALUES (4, TRUE, 1, 2, 0, 110700, 1, 2, 1, 3, 1);
INSERT INTO CAR ( ID, ACTIVE, PUBLISHER_ID, PUBLISHER_TYPE_ID, CHILD_SEATS, MILEAGE, CARCLASS_ID, MODEL_ID, FUELTYPE_ID, TRANSMISSIONTYPE_ID, LOCATION_ID) 
VALUES (5, TRUE, 1, 1, 0, 27100, 1, 2, 2, 2, 1);



--AGENTSERVICE---------------------------------------------

--AGENT
INSERT INTO AGENT ( ID, NAME, ADDRESS, LOCATION_ID, TAX_ID) 
VALUES (1, 'Agent 01', 'Trg Nikole Pašića 13, Beograd 11000, Srbija', 1, '123X-456Y-789Z');



--SCHEDULESERVICE---------------------------------------------

--RESERVATION-STATE-----------------------------------
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (1, 'CART');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (2, 'PENDING');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (3, 'CANCELED');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (4, 'PAID');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (5, 'REJECTED');
INSERT INTO RESERVATION_STATE ( ID, NAME) VALUES (6, 'COMPLETED');

--PUBLISHER-TYPE------------------------------------
INSERT INTO SCHEDULE_PUBLISHER_TYPE (ID, NAME) VALUES (1, 'USER');
INSERT INTO SCHEDULE_PUBLISHER_TYPE (ID, NAME) VALUES (2, 'AGENT');

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
INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (1, 1, 1, 2, 6);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (2, 1, 1, 2, 6);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (3, 1, 1, 2, 6);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (4, 1, 1, 2, 6);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (5, 1, 1, 1, 6);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (51, 1, 1, 2, 2);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (52, 2, 1, 2, 2);

INSERT INTO BUNDLE (ID, USER_ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, STATE_ID)
VALUES (53, 1, 1, 2, 4);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (51, 51, 1, null, PARSEDATETIME('1 Oct 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Oct 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 250, 100, TRUE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (52, 52, 1, null, PARSEDATETIME('2 Oct 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Oct 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 200, 0, FALSE );


--RESERVATION----------------------------------------
INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (1, 1, 1, 5, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 250, 100, TRUE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (2, 2, 1, 2, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 200, 0, FALSE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (3, 3, 1, 1, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 150, 0, TRUE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (4, 4, 1, 4, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 250, 0, FALSE );

------------

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (5, 1, 2, 5, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 140, 0, TRUE);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (6, 2, 2, 5, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 350, 0, FALSE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (7, 3, 2, 5, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 340, 90, TRUE);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (8, 4, 2, 4, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 344, 34, FALSE );

--------------

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (9, 1, 3, 1, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 350, 10, TRUE);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (10, 2, 3, 1, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 230, 12, FALSE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (11, 3, 3, 1, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 230, 10, TRUE);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (12, 4, 3, 1, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 30, 330, FALSE );

--------------

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (13, 1, 4, 4, PARSEDATETIME('1 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('1 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 230, 3, TRUE);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (14, 2, 4, 4, PARSEDATETIME('2 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('2 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 640, 500, FALSE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (15, 3, 4, 5, PARSEDATETIME('3 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('3 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 230, 11, TRUE);

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (16, 4, 4, 4, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 10, 10, FALSE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (17, 5, 5, null, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 10, NULL, FALSE );

INSERT INTO RESERVATION (ID, BUNDLE_ID, CAR_ID, RATING, START_DATE, END_DATE, TOTAL_PRICE, EXTRA_CHARGES, WARRANTY_INCLUDED)
VALUES (18, 5, 5, null, PARSEDATETIME('4 Jan 2020, 08:00:00 AM','dd MMM yyyy, hh:mm:ss a','en'), PARSEDATETIME('4 Jan 2020, 08:00:00 PM','dd MMM yyyy, hh:mm:ss a','en'), 10, NULL, FALSE );
--COMMENT-------------------------------
INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (1, 1, 1, 1, TRUE, '1591826330946', 'Beautiful car, great service');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (2, 1, 2, 1, TRUE, '1591826330946', 'Thank you, we hope to see you again soon!');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (3, 1, 1, 1, TRUE, '1591826330946', 'OK, Thank you!');

---

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (4, 1, 1, 2, TRUE, '1591826330946', 'The car was very dirty and there was a rattling noise coming from the engine.');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (5, 1, 2, 2, TRUE, '1591826330946', 'We are sorry to hear you had a bad experience. All cars are thoroughly cleaned. We also consider all complaints very seriously. We hope to see you again!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (6, 1, 1, 3, TRUE, '1591826330946', 'Never again!!');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (7, 1, 2, 3, TRUE, '1591826330946', 'Dear customer, we are sorry you had a bad experience. We still hope to remedy any complaints you may have, and hope to serve you in the future.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (8, 1, 1, 4, TRUE, '1591826330946', 'You get what you pay for');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (9, 1, 2, 4, TRUE, '1591826330946', 'Dear user, thank you for your comment and rating. We try to keep our services at low cost. The cars are maintained after every use.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (10, 1, 1, 5, TRUE, '1591826330946', 'Will reserve again');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (11, 1, 2, 5, TRUE, '1591826330946', 'Dear customer, we are happy to hear you are satisfied with our services. Thank you, we hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (12, 1, 1, 6, TRUE, '1591826330946', 'Such a great car');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (13, 1, 2, 6, TRUE, '1591826330946', 'Thank you for your comment and rating. We are happy to hear you are satisfied with our car and services. We hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (14, 1, 1, 7, TRUE, '1591826330946', 'I am 76 years old and just returned this car after reserving it for 5 days. Owned many cars in my lifetime,Corvett, Land rover,fords and on and on , cannot compare to this car. ');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (15, 1, 2, 7, TRUE, '1591826330946', 'Thank you for your rating and comment, we hope to see you again soon!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (16, 1, 1, 8, TRUE, '1591826330946', 'I gave 4 stars because of the price');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (17, 1, 2, 8, TRUE, '1591826330946', 'Dear customer, thank you for your reply and rating. Our prices are as low as possible, while still maintaining good services.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (18, 1, 1, 9, TRUE, '1591826330946', 'Just no.');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (19, 1, 2, 9, TRUE, '1591826330946', 'We are sorry you are not satisfied with our services. We hope to see you again and resolve any issues that you may have had.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (20, 1, 1, 10, TRUE, '1591826330946', 'Dirty, old, rusted, do not want to be caught in it ever again');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (21, 1, 2, 10, TRUE, '1591826330946', 'Dear customer, we are sorry to hear you had a bad experience. We hope to see you again and resolve any issues that you may have had.');
--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (22, 1, 1, 11, TRUE, '1591826330946', 'It looks nothing like in the picture. Someone crashed in the door and it does not close well. Had to slam the door at every cross-road');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (23, 1, 1, 13, TRUE, '1591826330946','You get what you pay for, it is worth the money but it is not in mint condition');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (24, 1, 2, 13, TRUE, '1591826330946','Dear user, thank you for your comment and rating. We try to keep our services at low cost. The cars are maintained after every use.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (25, 1, 1, 14, TRUE, '1591826330946','There was a weird smell, otherwise ok');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (26, 1, 2, 14, TRUE, '1591826330946','Dear customer, we are sorry to hear you had a bad experience. We hope to see you again and resolve any other issues you may have had.');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (27, 1, 1, 15, TRUE, '1591826330946','I rented this car for my sons prom, he was very happy');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (28, 1, 2, 15, TRUE, '1591826330946','Thank you, we hope to see you again soon!');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (29, 1, 1, 15, NULL, '1591826330946','Thanks');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (30, 1, 1, 15, NULL, '1591826330946','^&%"£$^%*)*_"&_s%^%!!!');

--

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (31, 1, 1, 16, TRUE, '1591826330946','It would not start so I was late, but it was ok after that');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (32, 1, 2, 16, TRUE, '1591826330946','Thank you for your rating and comment, we hope we managed to resolve your issues in time. Hope to see you again soon!');

INSERT INTO COMMENT (ID, PUBLISHER_ID, PUBLISHER_TYPE_ID, RESERVATION_ID, APPROVED, UNIX_TIMESTAMP, COMMENT)
VALUES (33, 1, 1, 16, NULL, '1591826330946','Hopefully you will fix the cars in the meantime.');



--USERSERVICE---------------------------------------------

--STATUS-----------------------
INSERT INTO STATUS (ID, NAME) VALUES (1,'PENDING_APPROVAL');
INSERT INTO STATUS (ID, NAME) VALUES (2,'ACTIVATED');
INSERT INTO STATUS (ID, NAME) VALUES (3,'BLOCKED');
INSERT INTO STATUS (ID, NAME) VALUES (4,'DELETED');



--ROLE-------------------------
INSERT INTO ROLE (ID, NAME) VALUES (1,'BASIC');
INSERT INTO ROLE (ID, NAME) VALUES (2,'AGENT');
INSERT INTO ROLE (ID, NAME) VALUES (3,'ADMIN');

--RESOURSE TYPE----------------
INSERT INTO RESOURSE_TYPE (ID, NAME) VALUES (1, 'USER');
INSERT INTO RESOURSE_TYPE (ID, NAME) VALUES (2, 'AGENT');

--PERMISSION-------------------
INSERT INTO PERMISSION (ID, NAME) VALUES (1, 'Add Car');
INSERT INTO PERMISSION (ID, NAME) VALUES (2, 'Add Car Image');
INSERT INTO PERMISSION (ID, NAME) VALUES (3, 'Add Car Unavailability');
INSERT INTO PERMISSION (ID, NAME) VALUES (4, 'Manage prices');
INSERT INTO PERMISSION (ID, NAME) VALUES (5, 'Manage reservations');
INSERT INTO PERMISSION (ID, NAME) VALUES (6, 'Chat');
INSERT INTO PERMISSION (ID, NAME) VALUES (7, 'Add car report');

--USER---------------------------
INSERT INTO USER (ID, ROLE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, STATUS_ID, EMAIL_VERIFIED, PASSWORD)
VALUES (1, 1, 'StudentFirstName', 'StudentLastName', 'student@mail.com', '021000001', 1, TRUE,
'$2a$10$iIlRkT3uRcprLxxzsagQzuiV6WCwN9Cyuu1gUCp7ACmIPzv5WxQHa');

INSERT INTO USER (ID, ROLE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, STATUS_ID, EMAIL_VERIFIED, PASSWORD)
VALUES (2, 2, 'AgentFirstName', 'AgentLastName', 'agent@mail.com', '021000002', 2, TRUE,
'$2a$10$iIlRkT3uRcprLxxzsagQzuiV6WCwN9Cyuu1gUCp7ACmIPzv5WxQHa');

INSERT INTO USER (ID, ROLE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, STATUS_ID, EMAIL_VERIFIED, PASSWORD)
VALUES (3, 3, 'AdminFirstName', 'AdminLastName', 'admin@mail.com', '021000003', 2, TRUE,
'$2a$10$iIlRkT3uRcprLxxzsagQzuiV6WCwN9Cyuu1gUCp7ACmIPzv5WxQHa');

--USER PERMISSION--------
-- ADD CAR
INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (1, 1, 1, '1589665206', 1, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (2, 2, 1, '1589665206', 2, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (3, 3, 1, '1589665206', null, null);

-- ADD CAR IMAGE
INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (4, 1, 2, '1589665206', 1, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (5, 2, 2, '1589665206', 2, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (6, 3, 2, '1589665206', null, null);

-- ADD CAR UNAVAILABILITY

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (7, 2, 3, '1589665206', 2, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (8, 3, 3, '1589665206', 2, null);

-- ADD PRICE
INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (9, 1, 4, '1589665206', 1, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (10, 2, 4, '1589665206', 2, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (11, 3, 4, '1589665206', null, null);

--

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (12, 1, 5, '1589665206', 1, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (13, 2, 5, '1589665206', 2, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (14, 3, 5, '1589665206', null, null);

--

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (15, 1, 6, '1589665206', 1, 1);

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (16, 2, 6, '1589665206', 2, 1);

--

INSERT INTO USER_PERMISSION (ID, USER_ID, PERMISSION_ID, UNIX_TIMESTAMP, RESOURSE_TYPE_ID, RESOURSE_ID)
VALUES (17, 2, 7, '1589665206', 2, 1);