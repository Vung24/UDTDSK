package com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.database;

public class FakeData {
    public static final String INSERT_USER = "insert into user(user_name, email,user_password) values" +
            "('Admin123','admin1@gmail.com','Password@123')";
    public static final String INSERT_PROFILE = "insert into profile(profile_id, user_id,full_name,date_of_birth,sex,diseases) values" +
            "('1','1','Nguyen Thi Sen','2003-11-28','Nữ','Huyết áp cao, Tiểu đường.')";
    public static final String INSERT_HEART_HEALTH = "insert into heart_health(heartbeat, heart_pressure,status,created_date,user_id) values" +
            "(74.0, 118.0, 'Bình thường', '2024-11-28', 1)," +
            "(71.5, 121.0, 'Bình thường', '2024-12-17', 1)," +
            "(83.0, 128.0, 'Cao', '2024-12-18', 1)," +
            "(78.5, 119.0, 'Bình thường', '2024-12-19', 1)," +
            "(80.0, 123.0, 'Bình thường', '2024-12-20', 1),"+
            "(75.5,120.0,'Bình thường','2024-12-23',1)," +
            "(65.5,110.0,'Thấp','2024-12-24',1)," +
            "(85.5,135.0,'Cao','2024-12-25',1)," +
            "(75.5,125.0,'Bình thường','2024-12-26',1)," +
            "(55.5,115.0,'Thấp','2024-12-27',1)";
    public static final String INSERT_CALORIES = "INSERT INTO calories (calories_intake, calories_burned, status, created_date, user_id) VALUES " +
            "(1900, 1500, 'Đủ', '2024-12-15', 1), " +
            "(1650, 1350, 'Quá ít', '2024-12-16', 1), " +
            "(2050, 1700, 'Quá nhiều', '2024-12-18', 1), " +
            "(1900, 1600, 'Đủ', '2024-12-19', 1), " +
            "(1600, 1230, 'Quá nhiều', '2024-12-20', 1), " +
            "(1200, 1100, 'Đủ', '2024-12-24', 1), " +
            "(2000, 1500, 'Quá nhiều', '2024-12-25', 1), " +
            "(1805, 1500, 'Quá ít', '2024-12-26', 1), " +
            "(1900, 1700, 'Đủ', '2024-12-23', 1)";
    public static final String INSERT_QUALITY_SLEEP = "insert into quality_sleep(start_sleep,finish_sleep,status,created_date,user_id) values" +
            "('23:00','05:00','Ngủ ít','2024-12-01', 1)," +
            "('00:00','05:30','Ngủ ít','2024-12-02', 1)," +
            "('23:30','06:00','Ngủ ít','2024-12-03', 1)," +
            "('00:00','07:30','Đủ','2024-12-04', 1)," +
            "('00:30','08:00','Ngủ ít','2024-12-05', 1)," +
            "('22:45','09:30','Ngủ quá nhiều','2024-12-06', 1)," +
            "('01:00','11:15','Ngủ quá nhiều','2024-12-07', 1)," +
            "('23:15','10:30','Ngủ quá nhiều','2024-12-08', 1)" ;
    public static final String INSERT_EXERCISE_PLAN = "INSERT INTO exercise_plan (exercise_name, exercise_type, exercise_plan_start, exercise_plan_finish, progress, status,score,goal, created_date, user_id)" +
            "VALUES" +
            "    ('Đẩy gối', 'gym', '2024-01-20', '2024-01-28', 100, 'Hoàn thành',100,100, '2024-01-18', 1)," +
            "    ('Tư thế cây', 'Yoga', '2024-11-25', '2024-02-01', 60, 'Chưa hoàn thành',60,100, '2024-01-23', 1)," +
            "    ('Bước dài ', 'gym', '2024-02-03', '2024-02-09', 50, 'Chưa hoàn thành',50,100, '2024-02-01', 1)," +
            "    ('Hít đất', 'gym', '2024-12-10', '2024-02-18', 100, 'Hoàn thành',100,100, '2024-02-08', 1)," +
            "    ('Tư thế cây', 'Yoga', '2024-02-15', '2024-02-22', 10, 'Chưa hoàn thành',10,100, '2024-02-13', 1)," +
            "    ('Kéo xà', 'gym', '2024-02-25', '2024-03-03', 80, 'Chưa hoàn thành',80,100, '2024-02-23', 1)," +
            "	('Tư thế plank', 'gym', '2024-12-29', '2024-01-10', 50, 'Chưa hoàn thành',50,100 ,'2024-12-27', 1)," +
            "    ('Tư thế em bé', 'Yoga', '2024-12-28', '2024-12-30', 30, 'Chưa hoàn thành',30,100, '2024-12-27', 1)," +
            "    ('Deadlift ', 'gym', '2024-12-30', '2024-01-02', 100, 'Hoàn thành',100,100, '2024-12-28', 1)," +
            "    ('Bài tập gập bụng', 'gym', '2024-01-01', '2024-01-20', 20, 'Chưa hoàn thành',20,100, '2024-12-28', 1);" ;
    public static final String INSERT_BMI_INDEX = "insert into bmi_index (height, weight,status,created_date,user_id) values" +
            "(1.7, 98, 'Béo', '2024-06-1', 1)," +
            "(1.7, 96, 'Béo', '2024-07-18', 1),"+
            "(1.7, 92, 'Thừa cân', '2024-08-15', 1),"+
            "(1.7, 85, 'Bình thường', '2024-09-30', 1)," +
            "(1.71, 76, 'Bình thường', '2024-10-29', 1)," +
            "(1.7, 67, 'Bình thường', '2024-11-24', 1)," +
            "(1.7, 62.5, 'Bình thường', '2024-12-20', 1),"+
            "(1.7, 62.2, 'Bình thường', '2024-12-22', 1)," +
            "(1.7, 62, 'Bình thường', '2024-12-25', 1)," +
            "(1.7, 60.5, 'Bình thường', '2024-12-27', 1);" ;
}
