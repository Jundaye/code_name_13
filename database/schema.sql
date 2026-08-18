create schema board;

use board;

create table board (
    board_id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    writer VARCHAR(50) NOT NULL,
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0,
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP
);
#drop table board;


create table comment (
	comment_id INT AUTO_INCREMENT PRIMARY KEY,
    board_id int(11) NOT NULL,
    comment_writer varchar(20) NOT NULL,
    comment_content varchar(500) NOT NULL,
    comment_regdate timestamp DEFAULT CURRENT_TIMESTAMP
);

create table users (
	user_id varchar(50) primary key,
    user_pw varchar(50) not null,
    user_name varchar(50) not null,
    reg_date DATE default (current_date)
);

#drop table comment;

CREATE TABLE board_like (
    board_id INT NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    reg_date DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (board_id, user_id),

    FOREIGN KEY (board_id)
        REFERENCES board(board_id)
        ON DELETE CASCADE,

    FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);