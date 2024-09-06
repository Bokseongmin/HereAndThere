CREATE TABLE `users` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_name` varchar(255),
  `user_email` varchar(255),
  `user_pass` varchar(255),
  `user_region` varchar(255),
  `role` integer default 1,
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `follows` (
  `following_user_id` integer,
  `followed_user_id` integer,
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `interests` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer,
  `name` varchar(255),
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `posts` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer,
  `title` varchar(255),
  `content` varchar(255),
  `status` varchar(255),
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `together` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer,
  `route_id` integer,
  `title` varchar(255),
  `content` varchar(255),
  `departure_date` timestamp,
  `arrival_date` timestamp,
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `replys` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `post_id` integer,
  `user_id` integer,
  `content` varchar(255),
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `reviews` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `tourist_attrs_id` integer,
  `user_id` integer,
  `content` varchar(255),
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `routes` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer,
  `tourist_attrs_list` varchar(255),
  `reg_date` timestamp,
  `mod_date` timestamp
);

CREATE TABLE `regions` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `lat` varchar(255),
  `long` varchar(255)
);

CREATE TABLE `tourist_attrs` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `region_id` integer,
  `name` varchar(255),
  `lat` varchar(255),
  `long` varchar(255)
);

ALTER TABLE `posts` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `follows` ADD FOREIGN KEY (`following_user_id`) REFERENCES `users` (`id`);

ALTER TABLE `follows` ADD FOREIGN KEY (`followed_user_id`) REFERENCES `users` (`id`);

ALTER TABLE `interests` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `replys` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `replys` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `reviews` ADD FOREIGN KEY (`tourist_attrs_id`) REFERENCES `tourist_attrs` (`id`);

ALTER TABLE `tourist_attrs` ADD FOREIGN KEY (`region_id`) REFERENCES `regions` (`id`);
