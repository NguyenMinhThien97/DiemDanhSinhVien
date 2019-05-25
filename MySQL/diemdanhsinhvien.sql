-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 25, 2019 lúc 05:28 AM
-- Phiên bản máy phục vụ: 10.1.39-MariaDB
-- Phiên bản PHP: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `diemdanhsinhvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cahoc`
--

CREATE TABLE `cahoc` (
  `macahoc` bigint(20) NOT NULL,
  `maphonghoc` bigint(20) DEFAULT NULL,
  `buoihoc` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `giobatdau` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `gioketthuc` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `mamonhoc` bigint(20) DEFAULT NULL,
  `thu` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

--
-- Đang đổ dữ liệu cho bảng `cahoc`
--

INSERT INTO `cahoc` (`macahoc`, `maphonghoc`, `buoihoc`, `giobatdau`, `gioketthuc`, `mamonhoc`, `thu`) VALUES
(1, 1, 'Sáng', '11:12', '20:00', 1, 2),
(2, 2, 'Chiều', '12:00', '14:45', 2, 7),
(3, 3, 'Chiều', '15:00', '5:30', 3, 7);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdiemdanh`
--

CREATE TABLE `chitietdiemdanh` (
  `machitietdiemdanh` bigint(20) NOT NULL,
  `madiemdanh` int(11) NOT NULL,
  `lydonghi` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `status` int(1) NOT NULL,
  `ngaydiemdanh` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdiemdanh`
--

INSERT INTO `chitietdiemdanh` (`machitietdiemdanh`, `madiemdanh`, `lydonghi`, `status`, `ngaydiemdanh`) VALUES
(16, 2, NULL, 1, '2019-05-15 00:00:00'),
(17, 2, NULL, 1, '2019-01-14 00:00:00'),
(18, 2, NULL, 1, '2019-03-23 00:00:00'),
(19, 2, NULL, 1, '2019-04-21 00:00:00'),
(20, 2, NULL, 1, '2019-05-04 00:00:00'),
(30, 1, NULL, 1, '2019-05-15 00:00:00'),
(31, 1, NULL, 1, '2019-01-14 00:00:00'),
(32, 1, NULL, 1, '2019-03-23 00:00:00'),
(33, 1, NULL, 1, '2019-04-21 00:00:00'),
(34, 1, NULL, 1, '2019-05-04 00:00:00'),
(36, 3, NULL, 1, '2019-05-15 00:00:00'),
(37, 3, NULL, 1, '2019-01-14 00:00:00'),
(38, 3, NULL, 1, '2019-03-23 00:00:00'),
(39, 3, NULL, 1, '2019-04-21 00:00:00'),
(40, 3, NULL, 1, '2019-05-04 00:00:00'),
(41, 3, NULL, 0, '2019-05-20 00:00:00'),
(42, 0, NULL, 1, '2019-05-15 00:00:00'),
(43, 0, NULL, 1, '2019-01-14 00:00:00'),
(44, 0, NULL, 1, '2019-03-23 00:00:00'),
(45, 0, NULL, 1, '2019-04-21 00:00:00'),
(46, 0, NULL, 1, '2019-05-04 00:00:00'),
(47, 0, NULL, 0, '2019-05-20 00:00:00'),
(50, 1, NULL, 2, '2019-05-20 00:00:00'),
(51, 1, NULL, 0, '0000-00-00 00:00:00'),
(52, 1, NULL, 1, '2019-05-20 00:00:00');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `diemdanh`
--

CREATE TABLE `diemdanh` (
  `madiemdanh` bigint(20) NOT NULL,
  `magiaovien` bigint(20) DEFAULT NULL,
  `masinhvien` int(11) NOT NULL,
  `mamonhoc` int(11) NOT NULL,
  `sotietdihoc` int(11) DEFAULT NULL,
  `sotietvang` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

--
-- Đang đổ dữ liệu cho bảng `diemdanh`
--

INSERT INTO `diemdanh` (`madiemdanh`, `magiaovien`, `masinhvien`, `mamonhoc`, `sotietdihoc`, `sotietvang`) VALUES
(0, 10052121, 15012345, 1, 20, 0),
(1, 10052121, 15094631, 1, 20, 0),
(2, 10052121, 15022571, 1, 20, 0),
(3, 10052121, 15056789, 1, 20, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `monhoc`
--

CREATE TABLE `monhoc` (
  `mamonhoc` bigint(20) NOT NULL,
  `chiso` int(11) DEFAULT NULL,
  `hocky` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `magiaovien` bigint(20) DEFAULT NULL,
  `namhoc` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `ngaybatdau` date DEFAULT NULL,
  `ngayketthuc` date DEFAULT NULL,
  `sotietlythuyet` int(11) DEFAULT NULL,
  `sotietthuchanh` int(11) DEFAULT NULL,
  `tenmonhoc` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `tongsotiet` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

--
-- Đang đổ dữ liệu cho bảng `monhoc`
--

INSERT INTO `monhoc` (`mamonhoc`, `chiso`, `hocky`, `magiaovien`, `namhoc`, `ngaybatdau`, `ngayketthuc`, `sotietlythuyet`, `sotietthuchanh`, `tenmonhoc`, `tongsotiet`) VALUES
(1, 3, '1', 10052121, '2019', '2019-04-01', '2019-08-30', 30, 30, 'Khoá luận tốt nghiệp', 60),
(2, 4, '1', 10052121, '2019', '2019-04-01', '2019-08-30', 30, 30, 'Kiến trúc máy tính', 60),
(3, 4, '1', 10052121, '2019', '2019-04-01', '2019-08-30', 45, 0, 'Tâm lý học đại cương', 45);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoidung`
--

CREATE TABLE `nguoidung` (
  `ma` bigint(20) NOT NULL,
  `chucvu` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `gioitinh` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `hinh` tinyblob,
  `matkhau` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `ngaysinh` datetime DEFAULT NULL,
  `sodienthoai` varchar(10) COLLATE utf8mb4_german2_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_german2_ci NOT NULL,
  `status` int(11) DEFAULT NULL,
  `ten` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `tenkhoa` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `tenlop` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `trinhdo` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`ma`, `chucvu`, `gioitinh`, `hinh`, `matkhau`, `ngaysinh`, `sodienthoai`, `email`, `status`, `ten`, `tenkhoa`, `tenlop`, `trinhdo`) VALUES
(10052121, 'Giảng Viên', 'Nữ', NULL, '1', '1985-01-10 00:00:00', '', 'minhtri456minh2@gmail.com', 1, 'Trần Thị Minh Khoa', 'CNTT', '', 'Tiến sĩ'),
(15012345, 'Sinh viên', 'Nam', NULL, '5', '1997-01-05 00:00:00', '0348868611', '', 0, 'Bùi Nguyễn Minh Trung', 'CNTT', 'DHCNTT11A', 'Đại học'),
(15022571, 'Sinh viên', 'Nam', NULL, '3', '1997-07-09 00:00:00', '0972983600', 'minhtri456minh@gmail.com', 0, 'Phạm Nguyễn Minh Trí', 'CNTT', 'DHCNTT11A', 'Đại học'),
(15056789, 'Sinh viên', 'Nữ', NULL, '4', '1997-01-05 00:00:00', '0123456789', '', 0, 'Nguyễn Thị Thanh Thảo', 'CNTT', 'DHCNTT11A', 'Đại học'),
(15094631, 'Sinh viên', 'Nam', NULL, '2', '1997-01-05 00:00:00', '0348868611', '', 0, 'Nguyễn Minh Thiên', 'CNTT', 'DHCNTT11A', 'Đại học');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phonghoc`
--

CREATE TABLE `phonghoc` (
  `maphonghoc` bigint(20) NOT NULL,
  `sochongoi` int(11) DEFAULT NULL,
  `tenphonghoc` varchar(255) COLLATE utf8mb4_german2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

--
-- Đang đổ dữ liệu cho bảng `phonghoc`
--

INSERT INTO `phonghoc` (`maphonghoc`, `sochongoi`, `tenphonghoc`) VALUES
(1, 50, 'H2.01'),
(2, 45, 'H6.03'),
(3, 50, 'V12.05');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `cahoc`
--
ALTER TABLE `cahoc`
  ADD PRIMARY KEY (`macahoc`);

--
-- Chỉ mục cho bảng `chitietdiemdanh`
--
ALTER TABLE `chitietdiemdanh`
  ADD PRIMARY KEY (`machitietdiemdanh`);

--
-- Chỉ mục cho bảng `diemdanh`
--
ALTER TABLE `diemdanh`
  ADD PRIMARY KEY (`madiemdanh`);

--
-- Chỉ mục cho bảng `monhoc`
--
ALTER TABLE `monhoc`
  ADD PRIMARY KEY (`mamonhoc`);

--
-- Chỉ mục cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`ma`);

--
-- Chỉ mục cho bảng `phonghoc`
--
ALTER TABLE `phonghoc`
  ADD PRIMARY KEY (`maphonghoc`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chitietdiemdanh`
--
ALTER TABLE `chitietdiemdanh`
  MODIFY `machitietdiemdanh` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
