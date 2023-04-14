-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Апр 14 2023 г., 15:11
-- Версия сервера: 5.7.17-log
-- Версия PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `str`
--

-- --------------------------------------------------------

--
-- Структура таблицы `blackip`
--

CREATE TABLE `blackip` (
  `id` int(11) NOT NULL,
  `ip` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `blackip`
--

INSERT INTO `blackip` (`id`, `ip`) VALUES
(1, '0.0.0.0');

-- --------------------------------------------------------

--
-- Структура таблицы `boardsgame`
--

CREATE TABLE `boardsgame` (
  `id` int(11) NOT NULL,
  `game` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `boardsgame`
--

INSERT INTO `boardsgame` (`id`, `game`) VALUES
(7812, 'petter0|Admin|c;2|petter0'),
(7813, 'petter0'),
(7814, 'petter0|Admin|13;29|g;8|1;18|b;8|6;21|a;6|12;28|c;5|11;19|e;4|14;21|e;7|18;35|f;8|35;52|f;6|8;24|e;8|0;16|d;8|16;18|f;6|18;50|petter0'),
(7815, 'Anton|Admin|11;27|55;39|12;28|54;38|13;29|38;29|2;29|50;34|27;34|51;35|28;35|60;42|35;42|49;42|4;11|59;60|1;16|52;36|3;1|53;37|2;3|61;34|11;59|60;53|59;58|57;40|58;37|53;60|3;43|Anton'),
(7816, 'petter0|Admin|11;27|b;8|10;18|e;7|12;20|f;8|13;21|b;4|20;28|e;5|4;28|c;6|21;28|g;8|petter0'),
(7817, 'petter0|Admin|petter0'),
(7818, 'Anton|Admin|12;28|52;36|11;27|36;27|Admin'),
(7819, 'petter0|Admin|1;18|b;8|6;21|g;8|12;28|d;7|11;27|c;8|8;16|g;4|28;36|c;6|9;25|h;5|16;25|f;6|18;33|g;4|10;18|e;7|36;43|b;4|4;13|d;8|43;50|e;8|18;26|d;6|3;10|f;6|21;27|d;5|13;27|e;7|33;27|d;8|26;34|c;8|34;42|g;7|27;42|f;8|5;19|c;5|10;19|h;8|2;29|b;8|0;48|b;7|29;22|b;5|42;57|c;7|22;43|c;8|57;42|c;7|43;29|d;6|42;36|e;7|7;1|b;4|1;57|d;8|57;58|a;8|36;26|b;3|29;15|c;5|15;29|d;7|14;22|c;8|22;30|a;7|26;43|f;8|50;58|c;8|43;58|b;8|29;47|c;8|30;38|b;8|38;46|a;8|47;29|b;8|46;54|c;8|58;43|b;8|43;26|a;7|29;36|b;8|48;56|a;7|54;62|b;6|56;48|a;6|48;55|Admin'),
(7820, 'petter0|Admin|petter0|Admin|11;27|d;1|petter0'),
(7821, 'petter0|Admin|11;27|b;8|10;18|e;7|18;26|e;5|9;17|g;8|17;26|f;6|petter0'),
(7822, 'petter0|Admin|11;19|b;8|9;25|e;7|10;26|d;8|3;11|h;4|11;3|g;4|3;11|e;5|19;26|h;4|4;3|f;4|11;4|e;5|4;3|c;6|13;21|b;4|3;4|c;2|2;20|g;8|14;22|d;7|5;14|c;8|4;3|a;1|15;22|e;8|8;16|g;4|1;18|f;8|16;24|c;8|21;29|d;5|22;29|d;7|petter0'),
(7823, 'petter0|Admin|6;21|g;8|11;27|b;8|1;18|e;7|12;20|f;8|27;35|b;4|14;21|f;6|21;29|d;5|5;14|c;3|7;4|petter0'),
(7824, 'petter0|Admin|11;27|b;8|10;18|e;7|2;29|f;8|6;21|g;8|1;16|c;6|3;1|d;6|12;20|d;4|petter0'),
(7825, 'petter0');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  `win` int(11) NOT NULL,
  `over` int(11) NOT NULL,
  `games` text NOT NULL,
  `statics` int(11) NOT NULL,
  `boardid` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `nich` int(11) NOT NULL,
  `ban` int(11) NOT NULL,
  `chatBan` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `win`, `over`, `games`, `statics`, `boardid`, `money`, `nich`, `ban`, `chatBan`) VALUES
(1, 'Anton', 'Dert869&&', 37, 13953, '4', 0, NULL, 182306724, 2, 0, 0),
(2, 'Admin', '1', 145, 3018, '4', 0, NULL, 2070531, 2, 0, 0),
(3, 'petter0', 'bsdfhabdsafbhsdabfjhas', 475, 134, '58', 0, NULL, 219787, 3, 0, 0),
(4, 'peyton', 'bsdfhabdsafbhsdabfjhas', 73, 85, '1', 0, NULL, 201018, 3, 0, 0),
(5, 'Pyton', 'bsdfhabdsafbhsdabfjhas', 84, 86, '7', 0, NULL, 200899, 3, 0, 0),
(6, 'luis', 'bsdfhabdsafbhsdabfjhas', 38, 69, '1', 0, NULL, 200845, 3, 0, 0),
(7, 'Sibgatulin', 'bsdfhabdsafbhsdabfjhas', 210, 64, '5', 0, NULL, 200859, 3, 0, 0),
(8, 'Marshall', 'bsdfhabdsafbhsdabfjhas', 77, 113, '4', 0, NULL, 200948, 3, 0, 0),
(9, 'TonnyS', 'bsdfhabdsafbhsdabfjhas', 95, 1379, '3', 0, NULL, 200886, 3, 0, 0),
(10, 'jimmys', 'bsdfhabdsafbhsdabfjhas', 41, 64, '5', 0, NULL, 200777, 3, 0, 0),
(11, 'Bot', 'bsdfhabdsafbhsdabfjhas', 6285, 59, '3', 0, NULL, 227726, 3, 0, 0),
(12, 'Harryk', 'bsdfhabdsafbhsdabfjhas', 144, 181, '2', 0, NULL, 200777, 3, 0, 0),
(13, 'IgorWin', 'bsdfhabdsafbhsdabfjhas', 91, 152, '7', 0, NULL, 200777, 3, 0, 0),
(14, 'GameChessM', 'bsdfhabdsafbhsdabfjhas', 93, 99, '6', 0, NULL, 200800, 3, 0, 0),
(15, 'Chesses', 'bsdfhabdsafbhsdabfjhas', 81, 155, '22', 0, NULL, 200813, 3, 0, 0),
(16, 'Antoni', 'Dert869uu', 0, 0, '0', 0, 0, 500, 0, 0, 0),
(17, 'AntonSibg', 'Dert86911', 0, 0, '0', 0, 0, 500, 0, 0, 0),
(18, 'AntonSibg1', 'Dert86911', 0, 0, '0', 0, 0, 500, 0, 0, 0),
(19, 'AntonAdmin', 'Fightmas', 0, 0, '0', 0, 0, 500, 0, 0, 0);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `blackip`
--
ALTER TABLE `blackip`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `boardsgame`
--
ALTER TABLE `boardsgame`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `blackip`
--
ALTER TABLE `blackip`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT для таблицы `boardsgame`
--
ALTER TABLE `boardsgame`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7826;
--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
