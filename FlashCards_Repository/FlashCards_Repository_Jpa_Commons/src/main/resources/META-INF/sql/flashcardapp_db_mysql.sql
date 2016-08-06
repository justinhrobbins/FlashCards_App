CREATE TABLE `batchloadingreceipt` (
  `BatchLoadingId` bigint(20) NOT NULL AUTO_INCREMENT,
  `CreatedUserId` bigint(20) NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedUserId` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `EndTime` datetime DEFAULT NULL,
  `FailureCount` int(11) NOT NULL,
  `StartTime` datetime NOT NULL,
  `SuccessCount` int(11) NOT NULL,
  `Type` varchar(255) NOT NULL,
  PRIMARY KEY (`BatchLoadingId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `flashcard` (
  `FlashCardId` bigint(20) NOT NULL AUTO_INCREMENT,
  `CreatedUserId` bigint(20) NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedUserId` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `Answer` longtext NOT NULL,
  `Question` varchar(255) NOT NULL,
  PRIMARY KEY (`FlashCardId`),
  UNIQUE KEY `UK_tkujkvamlndkijuwnywiy2exc` (`Question`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `flashcard_link` (
  `FlashCardId` bigint(20) NOT NULL,
  `Link` varchar(255) NOT NULL,
  `link_idx` int(11) NOT NULL,
  PRIMARY KEY (`FlashCardId`,`link_idx`),
  CONSTRAINT `FK_5jphwr6a2siei2sf2kskyr735` FOREIGN KEY (`FlashCardId`) REFERENCES `flashcard` (`FlashCardId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `tag` (
  `TagId` bigint(20) NOT NULL AUTO_INCREMENT,
  `CreatedUserId` bigint(20) NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedUserId` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `TagName` varchar(255) NOT NULL,
  PRIMARY KEY (`TagId`),
  UNIQUE KEY `UK_fyvfcvivk8s79m5kwwfajaum7` (`TagName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `flashcard_tag` (
  `FlashCardId` bigint(20) NOT NULL,
  `TagId` bigint(20) NOT NULL,
  PRIMARY KEY (`FlashCardId`,`TagId`),
  KEY `FK_i06wpylx729iddlqpkoyc083g` (`TagId`),
  CONSTRAINT `FK_1cpsb1uemo8n2ihoxtlewb2l8` FOREIGN KEY (`FlashCardId`) REFERENCES `flashcard` (`FlashCardId`),
  CONSTRAINT `FK_i06wpylx729iddlqpkoyc083g` FOREIGN KEY (`TagId`) REFERENCES `tag` (`TagId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `UserId` bigint(20) NOT NULL AUTO_INCREMENT,
  `CreatedUserId` bigint(20) NOT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedUserId` bigint(20) DEFAULT NULL,
  `UpdatedDate` datetime DEFAULT NULL,
  `Country` varchar(255) DEFAULT NULL,
  `Email` varchar(255) NOT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `FullName` varchar(255) DEFAULT NULL,
  `Language` varchar(255) DEFAULT NULL,
  `LastLoginDate` datetime DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Nickname` varchar(255) DEFAULT NULL,
  `OpenId` varchar(255) NOT NULL,
  PRIMARY KEY (`UserId`),
  UNIQUE KEY `UK_70o6t0tjywno6j28dnn86idtf` (`OpenId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

