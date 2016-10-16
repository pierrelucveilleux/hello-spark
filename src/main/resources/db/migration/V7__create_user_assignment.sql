CREATE table user_assignment (
  user_id VARCHAR(256),
  account_id VARCHAR(256),

  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (account_id) REFERENCES account(id)
);