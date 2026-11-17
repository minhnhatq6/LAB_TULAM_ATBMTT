-- 1. Tạo Database và 2 bảng app_users, crypto_history
IF DB_ID(N'LAB2_MaHoaHienDai') IS NULL
    CREATE DATABASE LAB2_MaHoaHienDai;
GO
USE LAB2_MaHoaHienDai;
GO
IF OBJECT_ID(N'dbo.app_users', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.app_users (
        user_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        username NVARCHAR(40) NOT NULL UNIQUE,
        salt VARBINARY(16) NOT NULL,
        password_hash VARBINARY(32) NOT NULL,
        created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
    );
END;
GO
IF OBJECT_ID(N'dbo.crypto_history', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.crypto_history (
        history_id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        algorithm NVARCHAR(40) NOT NULL,
        operation NVARCHAR(16) NOT NULL,
        app_username NVARCHAR(40) NULL,
        input_length INT NOT NULL,
        output_length INT NOT NULL,
        created_at DATETIME2(0) NOT NULL DEFAULT SYSUTCDATETIME()
    );
END;
GO

-- 2. Tạo tài khoản lab2_user (Mật khẩu: LabUser@2026)
USE master;
GO
IF SUSER_ID(N'lab2_user') IS NULL
    CREATE LOGIN lab2_user WITH PASSWORD = 'LabUser@2026', CHECK_POLICY = ON;
GO
USE LAB2_MaHoaHienDai;
GO
IF DATABASE_PRINCIPAL_ID(N'lab2_user') IS NULL
    CREATE USER lab2_user FOR LOGIN lab2_user;
GO
ALTER ROLE db_datareader ADD MEMBER lab2_user;
ALTER ROLE db_datawriter ADD MEMBER lab2_user;
GO