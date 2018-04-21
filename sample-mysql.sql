-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 06, 2015 at 05:02 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ucu`
--

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE IF NOT EXISTS `results` (
`id` int(5) NOT NULL,
  `subCode` varchar(10) NOT NULL,
  `intmark` float NOT NULL,
  `extmark` float NOT NULL,
  `total` float NOT NULL,
  `pass` tinyint(1) NOT NULL,
  `enrol_no` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`id`, `subCode`, `intmark`, `extmark`, `total`, `pass`, `enrol_no`) VALUES
(1, 'math', 20, 30, 50, 1, '21300'),
(2, 'sst', 50, 30, 50, 1, '1000'),
(3, 'math', 90, 10, 100, 0, '200'),
(5, 'math', 12, 12, 24, 1, 'student'),
(6, 'eng', 30, 90, 120, 0, '12345'),
(7, 'sst', 50, 30, 80, 1, '100');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
`id` int(5) NOT NULL,
  `name` varchar(40) NOT NULL,
  `enrol_no` varchar(20) NOT NULL,
  `DOB` date NOT NULL,
  `address` varchar(100) NOT NULL,
  `fathers_name` varchar(100) NOT NULL,
  `batch` varchar(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `name`, `enrol_no`, `DOB`, `address`, `fathers_name`, `batch`) VALUES
(1, 'Bosco Suzan', '2', '2015-04-10', 'Ntinda', 'Mr. Emma', '3000'),
(2, 'Robin Hood', '23400', '2015-04-06', 'Mukono', 'Mr. John', '2011'),
(3, 'Student Candidate', 'student', '2015-04-15', 'Kikoni', 'Mr. teacher', '1990');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE IF NOT EXISTS `subject` (
`id` int(5) NOT NULL,
  `subName` varchar(50) NOT NULL,
  `subCode` varchar(15) NOT NULL,
  `semester` varchar(3) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`id`, `subName`, `subCode`, `semester`) VALUES
(1, 'Social Studies', 'sst', 'I'),
(2, 'Mathematics', 'math', 'II');

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE IF NOT EXISTS `teachers` (
`id` int(3) NOT NULL,
  `username` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `year` varchar(100) NOT NULL,
  `subCode` varchar(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`id`, `username`, `name`, `year`, `subCode`) VALUES
(1, 'teacher', 'Mr. Head Teacher', '2012', 'math'),
(2, 'teacher2', 'Mr. Deputy Head Teacher', '1990', 'eng');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`userid` int(5) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `username`, `password`, `role`) VALUES
(1, 'admin', 'admin', 'Administrator'),
(2, 'teacher', 'teacher', 'teacher'),
(3, 'data', 'data', 'Data entry Operator'),
(5, 'student', 'student', 'student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `results`
--
ALTER TABLE `results`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `stNo` (`name`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `subCode` (`subCode`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
MODIFY `id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
MODIFY `id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `subject`
--
ALTER TABLE `subject`
MODIFY `id` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
MODIFY `id` int(3) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `userid` int(5) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
