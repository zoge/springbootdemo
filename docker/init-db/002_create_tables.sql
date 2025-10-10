USE DemoDB;
GO

-- ========================================================
-- Alap adattáblák létrehozása
-- ========================================================

CREATE TABLE Person (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    FirstName NVARCHAR(100) NOT NULL,
    LastName NVARCHAR(100) NOT NULL,
    BirthDate DATE NULL,
    CreatedAt DATETIME2 DEFAULT SYSDATETIME(),
    UpdatedAt DATETIME2 DEFAULT SYSDATETIME()
);

CREATE TABLE Address (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    PersonId INT NOT NULL,
    AddressType NVARCHAR(20) CHECK (AddressType IN ('PERMANENT', 'TEMPORARY')),
    Street NVARCHAR(200) NOT NULL,
    City NVARCHAR(100) NOT NULL,
    ZipCode NVARCHAR(20) NOT NULL,
    Country NVARCHAR(100) NOT NULL,
    CreatedAt DATETIME2 DEFAULT SYSDATETIME(),
    UpdatedAt DATETIME2 DEFAULT SYSDATETIME()
    FOREIGN KEY (PersonId) REFERENCES Person(Id)
);

CREATE TABLE Contact (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    AddressId INT NOT NULL,
    ContactType NVARCHAR(50) CHECK (ContactType IN ('EMAIL', 'PHONE', 'MOBILE', 'OTHER')),
    ContactValue NVARCHAR(255) NOT NULL,
    CreatedAt DATETIME2 DEFAULT SYSDATETIME(),
    UpdatedAt DATETIME2 DEFAULT SYSDATETIME()
    FOREIGN KEY (AddressId) REFERENCES Address(Id)
);

-- csak egy Állandó és egy ideiglenes címe lehet egy személynek
CREATE UNIQUE INDEX Address_PersonId_IDX ON DemoDB.dbo.Address (PersonId,AddressType);