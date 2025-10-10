USE DemoDB;
GO

INSERT INTO Person (FirstName, LastName, BirthDate)
VALUES 
    ('Kovács', 'Péter', '1985-06-15'),
    ('Nagy', 'Anna', '1992-03-10');

INSERT INTO Address (PersonId, AddressType, Street, City, ZipCode, Country)
VALUES 
    (1, 'PERMANENT', 'Fő utca 12.', 'Budapest', '1111', 'Hungary'),
    (1, 'TEMPORARY', 'Kert utca 5.', 'Szeged', '6720', 'Hungary'),
    (2, 'PERMANENT', 'Petőfi utca 7.', 'Debrecen', '4025', 'Hungary');

INSERT INTO Contact (AddressId, ContactType, ContactValue)
VALUES
    (1, 'EMAIL', 'peter.kovacs@example.com'),
    (1, 'PHONE', '+36-30-555-1111'),
    (2, 'EMAIL', 'peter.temp@example.com'),
    (3, 'PHONE', '+36-70-222-3333');