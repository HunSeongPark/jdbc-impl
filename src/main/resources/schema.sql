DROP TABLE IF EXISTS members CASCADE;
DROP TABLE IF EXISTS teams CASCADE;
DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE teams (
    team_id bigint NOT NULL AUTO_INCREMENT,
    name    varchar(10) NOT NULL,
    PRIMARY KEY (team_id)
);

CREATE TABLE members (
    member_id   bigint NOT NULL AUTO_INCREMENT,
    team_id     bigint NOT NULL,
    name        varchar(10) NOT NULL,
    age         int NOT NULL DEFAULT 0,
    PRIMARY KEY (member_id),
    CONSTRAINT fk_members_to_teams FOREIGN KEY (team_id) REFERENCES teams (team_id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE orders (
    order_id   bigint NOT NULL AUTO_INCREMENT,
    member_id    bigint NOT NULL,
    state      enum('REQ', 'COMP', 'REJ') DEFAULT 'REQ' NOT NULL,
    PRIMARY KEY (order_id),
    CONSTRAINT fk_orders_to_members FOREIGN KEY (member_id) REFERENCES members (member_id) ON DELETE RESTRICT ON UPDATE RESTRICT
);