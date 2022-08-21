CREATE TABLE company(
company_id INT AUTO_INCREMENT PRIMARY KEY,
company_name VARCHAR(100),
company_description VARCHAR(200)
);

CREATE TABLE developer(
developer_id INT AUTO_INCREMENT PRIMARY KEY,
first_name varchar(50) NOT NULL,
last_name varchar(100) NOT NULL,
age INT,
gender VARCHAR(10),
city VARCHAR(100),
salary MEDIUMINT,
company_id INT,
FOREIGN KEY (company_id) REFERENCES company(company_id) ON DELETE SET NULL
);

CREATE TABLE skill(
skill_id INT AUTO_INCREMENT PRIMARY KEY,
dev_language VARCHAR(50),
skill_level VARCHAR(20)
);

CREATE TABLE developers_skills(
developer_id INT NOT NULL,
skill_id INT NOT NULL,
FOREIGN KEY (developer_id) REFERENCES developer(developer_id) ON DELETE CASCADE,
FOREIGN KEY (skill_id) REFERENCES skill(skill_id) ON DELETE CASCADE,
UNIQUE (developer_id, skill_id)
);

CREATE TABLE customer(
customer_id INT AUTO_INCREMENT PRIMARY KEY,
customer_name VARCHAR(100),
customer_descriptions VARCHAR(100)
);

CREATE TABLE project(
project_id INT AUTO_INCREMENT PRIMARY KEY,
project_name VARCHAR(100),
project_description VARCHAR(200),
release_date DATE,
company_id INT,
customer_id INT,
FOREIGN KEY (company_id) REFERENCES company (company_id) ON DELETE SET NULL,
FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE SET NULL
);

CREATE TABLE developers_projects(
developer_id INT NOT NULL,
project_id INT NOT NULL,
FOREIGN KEY (developer_id) REFERENCES developer(developer_id) ON DELETE CASCADE,
FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE,
UNIQUE (developer_id, project_id)
);