-- Sample seed data

INSERT INTO users (username, email, password, full_name, role, enabled, created_date, updated_date)
VALUES ('admin', 'admin@campusfinder.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoO7SGT9zZgMK3F3s1z3Wz0bA6z1uT3d9O', 'System Admin', 'ADMIN', true, NOW(), NOW());
-- Note: this password hash is a placeholder. Register a real user via /api/auth/register
-- (it will get a proper BCrypt hash), then manually set its role to ADMIN in the database
-- if you need an admin account for testing.

INSERT INTO campuses (university_name, campus_name, description, address, city, province, country,
                      latitude, longitude, website, contact_number, email, average_tuition_fee,
                      admission_requirements, campus_image, created_date, updated_date)
VALUES ('University of Karachi', 'Main Campus', 'Premier public university in Sindh.',
        'University Road', 'Karachi', 'Sindh', 'Pakistan', 24.9315, 67.1172,
        'https://uok.edu.pk', '+92-21-99261300', 'info@uok.edu.pk', 150000.00,
        'Minimum 60% in Intermediate/A-Levels', 'https://example.com/uok.jpg', NOW(), NOW());

INSERT INTO campuses (university_name, campus_name, description, address, city, province, country,
                      latitude, longitude, website, contact_number, email, average_tuition_fee,
                      admission_requirements, campus_image, created_date, updated_date)
VALUES ('NED University', 'Main Campus', 'Leading engineering university in Karachi.',
        'University Road', 'Karachi', 'Sindh', 'Pakistan', 24.9340, 67.1128,
        'https://neduet.edu.pk', '+92-21-99261261', 'info@neduet.edu.pk', 220000.00,
        'NED Entry Test required, minimum 60% in FSc Pre-Engineering', 'https://example.com/ned.jpg', NOW(), NOW());

INSERT INTO programs (program_name, degree_type, duration, description, tuition_fee, eligibility_criteria, campus_id)
VALUES ('Computer Science', 'BS', '4 years', 'Bachelor of Science in Computer Science', 180000.00,
        'Intermediate with Mathematics, 60% marks', 1);

INSERT INTO programs (program_name, degree_type, duration, description, tuition_fee, eligibility_criteria, campus_id)
VALUES ('Software Engineering', 'BS', '4 years', 'Bachelor of Science in Software Engineering', 200000.00,
        'FSc Pre-Engineering, NED Entry Test', 2);