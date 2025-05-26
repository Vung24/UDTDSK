package com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.database;

public class FakeData {
    public static final String INSERT_USER = "insert into user(user_name, email,user_password) values" +
            "('Admin123','admin1@gmail.com','Password@123')";
    public static final String INSERT_PROFILE = "insert into profile(profile_id, user_id,full_name,date_of_birth,sex,diseases) values" +
            "('1','1','Nguyen Tat Vung','2004-05-03','Nam','Huyết áp cao, Tiểu đường.')";
    public static final String INSERT_HEART_HEALTH = "insert into heart_health(heartbeat, heart_pressure,status,created_date,user_id) values" +
            "(74.0, 118.0, 'Bình thường', '2025-04-20', 1)," +
            "(71.5, 121.0, 'Bình thường', '2025-04-22', 1)," +
            "(83.0, 128.0, 'Cao', '2025-04-25', 1)," +
            "(78.5, 119.0, 'Bình thường', '2025-04-28', 1)," +
            "(80.0, 123.0, 'Bình thường', '2025-05-02', 1)," +
            "(75.5, 120.0, 'Bình thường', '2025-05-05', 1)," +
            "(65.5, 110.0, 'Thấp', '2025-05-08', 1)," +
            "(85.5, 135.0, 'Cao', '2025-05-12', 1)," +
            "(75.5, 125.0, 'Bình thường', '2025-05-15', 1)," +
            "(55.5, 115.0, 'Thấp', '2025-05-20', 1)";
    public static final String INSERT_CALORIES = "INSERT INTO calories (calories_intake, calories_burned, status, created_date, user_id) VALUES " +
            "(1900, 1500, 'Đủ', '2025-04-20', 1), " +
            "(1650, 1350, 'Quá ít', '2025-04-22', 1), " +
            "(2050, 1700, 'Quá nhiều', '2025-04-25', 1), " +
            "(1900, 1600, 'Đủ', '2025-04-28', 1), " +
            "(1600, 1230, 'Quá nhiều', '2025-05-02', 1), " +
            "(1200, 1100, 'Đủ', '2025-05-05', 1), " +
            "(2000, 1500, 'Quá nhiều', '2025-05-08', 1), " +
            "(1805, 1500, 'Quá ít', '2025-05-12', 1), " +
            "(1900, 1700, 'Đủ', '2025-05-15', 1)";
    public static final String INSERT_QUALITY_SLEEP = "insert into quality_sleep(start_sleep,finish_sleep,status,created_date,user_id) values" +
            "('23:00','05:00','Ngủ ít','2025-04-20', 1)," +
            "('00:00','05:30','Ngủ ít','2025-04-22', 1)," +
            "('23:30','06:00','Ngủ ít','2025-04-25', 1)," +
            "('00:00','07:30','Đủ','2025-04-28', 1)," +
            "('00:30','08:00','Ngủ ít','2025-05-02', 1)," +
            "('22:45','09:30','Ngủ quá nhiều','2025-05-05', 1)," +
            "('01:00','11:15','Ngủ quá nhiều','2025-05-08', 1)," +
            "('23:15','10:30','Ngủ quá nhiều','2025-05-12', 1)";
    public static final String INSERT_EXERCISE_PLAN = "INSERT INTO exercise_plan (exercise_name, exercise_type, exercise_plan_start, exercise_plan_finish, progress, status,score,goal, created_date, user_id)" +
            "VALUES" +
            "    ('Đẩy gối', 'gym', '2025-04-20', '2025-04-28', 100, 'Hoàn thành',100,100, '2025-04-18', 1)," +
            "    ('Tư thế cây', 'Yoga', '2025-04-22', '2025-05-01', 60, 'Chưa hoàn thành',60,100, '2025-04-20', 1)," +
            "    ('Bước dài', 'gym', '2025-04-25', '2025-05-02', 50, 'Chưa hoàn thành',50,100, '2025-04-23', 1)," +
            "    ('Hít đất', 'gym', '2025-04-28', '2025-05-06', 100, 'Hoàn thành',100,100, '2025-04-26', 1)," +
            "    ('Tư thế cây', 'Yoga', '2025-05-02', '2025-05-09', 10, 'Chưa hoàn thành',10,100, '2025-04-30', 1)," +
            "    ('Kéo xà', 'gym', '2025-05-05', '2025-05-13', 80, 'Chưa hoàn thành',80,100, '2025-05-03', 1)," +
            "    ('Tư thế plank', 'gym', '2025-05-08', '2025-05-16', 50, 'Chưa hoàn thành',50,100, '2025-05-06', 1)," +
            "    ('Tư thế em bé', 'Yoga', '2025-05-12', '2025-05-15', 30, 'Chưa hoàn thành',30,100, '2025-05-10', 1)," +
            "    ('Deadlift', 'gym', '2025-05-15', '2025-05-20', 100, 'Hoàn thành',100,100, '2025-05-13', 1)," +
            "    ('Bài tập gập bụng', 'gym', '2025-05-18', '2025-05-25', 20, 'Chưa hoàn thành',20,100, '2025-05-16', 1)";
    public static final String INSERT_BMI_INDEX = "insert into bmi_index (height, weight,status,created_date,user_id) values" +
            "(1.7, 98, 'Béo', '2025-04-20', 1)," +
            "(1.7, 96, 'Béo', '2025-04-22', 1)," +
            "(1.7, 92, 'Thừa cân', '2025-04-25', 1)," +
            "(1.7, 85, 'Bình thường', '2025-04-28', 1)," +
            "(1.71, 76, 'Bình thường', '2025-05-02', 1)," +
            "(1.7, 67, 'Bình thường', '2025-05-05', 1)," +
            "(1.7, 62.5, 'Bình thường', '2025-05-08', 1)," +
            "(1.7, 62.2, 'Bình thường', '2025-05-12', 1)," +
            "(1.7, 62, 'Bình thường', '2025-05-15', 1)," +
            "(1.7, 60.5, 'Bình thường', '2025-05-20', 1)";
}