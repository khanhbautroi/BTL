-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Generation Time: May 04, 2025 at 05:40 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qlthuvien`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `username`, `password`) VALUES
(1, 'trannamkhanh', '$2a$10$tRKqkSa.KC7HlC6jStV35ukNMaVIhIXVHkN1bu3Iv.SSpaZv65UCK'),
(2, 'legiakhanh', '$2a$10$dxS8BE6p/KP2ZFySLGRpEe5POgbIssykP9vI0VQX1vvvsf0cyVFhi'),
(3, 'nguyentuanminh', '$2a$10$BAh1OibKSex7szNfLmZU5e46RQRURpEgAQBfnw33DZ/e30RRRsAW6'),
(4, 'luongminhson', '$2a$10$OnvQFgBRLOLql/G/vTjdGepBtyu0fLS.N6seJCrXI22ER4i1kVktG'),
(5, 'admin', '210404'),
(6, 'admin1', '123456'),
(7, 'admin2', '$2a$10$scbWyVQIWKmbYZ/LiA/VXeQqiyC4phOIbA8u.izz6IHIqP/Z3iBIG');

-- --------------------------------------------------------

--
-- Table structure for table `muon_tra_sach`
--

CREATE TABLE `muon_tra_sach` (
  `id` int(11) NOT NULL,
  `maSV` varchar(50) NOT NULL,
  `maS` varchar(50) NOT NULL,
  `tenSach` varchar(255) DEFAULT NULL,
  `ngayMuon` timestamp NOT NULL DEFAULT current_timestamp(),
  `ngayTraDuKien` date NOT NULL,
  `ngayTraThucTe` date DEFAULT NULL,
  `phiMuon` decimal(10,2) DEFAULT 0.00,
  `tinhTrangMuon` varchar(50) DEFAULT 'Đang mượn'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `muon_tra_sach`
--

INSERT INTO `muon_tra_sach` (`id`, `maSV`, `maS`, `tenSach`, `ngayMuon`, `ngayTraDuKien`, `ngayTraThucTe`, `phiMuon`, `tinhTrangMuon`) VALUES
(1, '04', '0004', 'Nhật Ký Đặng Thùy Trâm', '2025-05-02 15:59:11', '2025-05-07', NULL, 140000.00, 'Đang mượn'),
(2, '03', '0004', 'Nhật Ký Đặng Thùy Trâm', '2025-05-02 16:01:54', '2025-05-02', '2025-05-03', 120000.00, 'Đã trả'),
(3, '04', '0003', 'Tôi thấy hoa vàng trên cỏ xanh', '2025-05-02 16:09:50', '2025-05-02', NULL, 120.00, 'Đang mượn');

-- --------------------------------------------------------

--
-- Table structure for table `ql_nv`
--

CREATE TABLE `ql_nv` (
  `maNV` varchar(10) NOT NULL,
  `tenNV` varchar(30) NOT NULL,
  `tuoi` varchar(3) NOT NULL,
  `gt` varchar(5) NOT NULL,
  `dc` varchar(20) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `ql_nv`
--

INSERT INTO `ql_nv` (`maNV`, `tenNV`, `tuoi`, `gt`, `dc`, `sdt`, `email`) VALUES
('1101', 'Nguyễn Xuân Hoàn', '21', 'Nam', 'Hải Dương', '0987884323', 'nxhoan@gmail.com'),
('1102', 'Bùi Hoàng Hiệp', '21', 'Nam', 'Hòa Bình', '0987648527', 'bhhiep@gmail.com'),
('1103', 'Đoàn Đắc Huy', '23', 'Nam', 'Bắc Bling', '0987645999', 'ddhuy@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `ql_sach`
--

CREATE TABLE `ql_sach` (
  `maS` varchar(10) NOT NULL,
  `tenS` varchar(30) NOT NULL,
  `tg` varchar(20) NOT NULL,
  `NamXB` varchar(15) NOT NULL,
  `gia` int(10) NOT NULL,
  `sl` int(11) DEFAULT NULL,
  `maNXB` varchar(10) NOT NULL,
  `ngonngu` varchar(15) NOT NULL,
  `tinhtrang` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `ql_sach`
--

INSERT INTO `ql_sach` (`maS`, `tenS`, `tg`, `NamXB`, `gia`, `sl`, `maNXB`, `ngonngu`, `tinhtrang`) VALUES
('0001', 'Đắc Nhân Tâm', 'Dale Carnegie', '1936', 30000, 12, '001', 'Tiếng Việt', 'Mới'),
('0002', 'Mắt biếc', 'Nguyễn Nhật Ánh', '1990', 61000, 15, '002', 'Tiếng Việt', 'Mới'),
('0003', 'Tôi thấy hoa vàng trên cỏ xanh', 'Nguyễn Nhật Ánh', '2010', 68000, 39, '003', 'Tiếng Việt', 'Mới'),
('0004', 'Nhật Ký Đặng Thùy Trâm', 'B.sĩ Đặng Thùy Trâm', '1968', 120000, 7, '004', 'Tiếng Việt', 'Mới');

-- --------------------------------------------------------

--
-- Table structure for table `ql_sv`
--

CREATE TABLE `ql_sv` (
  `ma` varchar(10) NOT NULL,
  `ten` varchar(30) NOT NULL,
  `gt` varchar(10) NOT NULL,
  `tuoi` varchar(3) NOT NULL,
  `dc` varchar(30) NOT NULL,
  `sdt` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `ql_sv`
--

INSERT INTO `ql_sv` (`ma`, `ten`, `gt`, `tuoi`, `dc`, `sdt`, `email`) VALUES
('01', 'Trần Nam Khánh', 'Nam', '21', 'Hà Nam', '0941655919', 'khanhtrannam12@gmail.com'),
('02', 'Le Gia Khánh', 'Nam', '21', 'Điện Biên', '0949090419', 'lgkhanh@gmail.com'),
('03', 'Lương Minh Sơn', 'Nam', '21', 'Hà Nội', '0965788345', 'lmson@gmail.com'),
('04', 'Nguyễn Tuấn Minh', 'Nam', '21', 'Thái Bình', '0968712399', 'ntminh@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `muon_tra_sach`
--
ALTER TABLE `muon_tra_sach`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `muon_tra_sach`
--
ALTER TABLE `muon_tra_sach`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
