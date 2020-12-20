-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Des 2020 pada 04.05
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tubeswarung`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `id_barang` varchar(255) NOT NULL,
  `namaBarang` varchar(255) NOT NULL,
  `jenisBarang` varchar(255) NOT NULL,
  `rasa` varchar(255) NOT NULL DEFAULT '-',
  `jenis` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `Harga` int(11) NOT NULL DEFAULT 0,
  `kuantitas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`id_barang`, `namaBarang`, `jenisBarang`, `rasa`, `jenis`, `username`, `Harga`, `kuantitas`) VALUES
('E01', 'Garnier Men', 'Etc', '-', 'Sabun', 'admin', 12000, 11),
('M01', 'Oreo', 'Makanan', 'Coklat', 'Snack', 'admin', 2000, 82),
('M02', 'Chiki', 'Makanan', 'Keju', 'Snack', 'admin', 1000, 75),
('Min01', 'Pop Ice', 'Minuman', 'Coklat', 'Bubuk', 'Diba15', 2000, 49);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `no` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `id_Barang` varchar(255) NOT NULL,
  `nominalUang` int(11) NOT NULL,
  `jumlahBeli` int(11) NOT NULL,
  `kembalian` int(11) NOT NULL,
  `tanggalPembelian` datetime(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`no`, `nama`, `id_Barang`, `nominalUang`, `jumlahBeli`, `kembalian`, `tanggalPembelian`) VALUES
(1, 'Asep', 'M01', 6000, 3, 0, '2020-12-15 21:49:06.257242'),
(2, 'Bayu', 'E01', 24000, 2, 26000, '2020-12-16 10:24:43.280035'),
(3, 'Dimas', 'M02', 3000, 3, 2000, '2020-12-17 08:52:41.225401'),
(4, 'Dimas', 'M01', 4000, 2, 1000, '2020-12-17 08:54:11.199839'),
(5, 'Dimas', 'M01', 10000, 2, 6000, '2020-12-17 10:19:12.150821'),
(6, 'Dimas', 'Min01', 3000, 1, 1000, '2020-12-17 10:21:52.323898'),
(7, 'Dimas', 'M02', 2000, 2, 0, '2020-12-18 07:43:45.993321'),
(8, 'Dimas', 'M01', 10000, 3, 4000, '2020-12-19 09:37:27.516255');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `No` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `umur` int(3) NOT NULL,
  `email` varchar(255) NOT NULL,
  `noTelp` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `level` varchar(255) NOT NULL DEFAULT 'karyawan',
  `status` varchar(255) NOT NULL DEFAULT 'Aktif'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`No`, `nama`, `username`, `umur`, `email`, `noTelp`, `password`, `level`, `status`) VALUES
(1, 'Admin', 'admin', 0, '', '', 'admin', 'admin', 'Aktif'),
(2, 'Dimas Bagas Saputro', 'Diba15', 19, 'dimazzbagazz@yahoo.co.id', '0895349060971', 'Tunjang1', 'karyawan', 'Aktif'),
(3, 'Agung Maulana Yusuf', 'agungmaulana21', 19, 'agungmaulana12@gmail.com', '0895349027281', 'maulanaYusuf12', 'karyawan', 'Tidak Aktif'),
(5, 'Alex', 'alex12', 20, 'alexfajar@gmail.com', '089273748392', 'mauMakan124', 'karyawan', 'Tidak Aktif');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`id_barang`),
  ADD KEY `username` (`username`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no`),
  ADD KEY `fk_idBarang` (`id_Barang`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`No`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `No` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD CONSTRAINT `barang_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`);

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `fk_idBarang` FOREIGN KEY (`id_Barang`) REFERENCES `barang` (`id_barang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
