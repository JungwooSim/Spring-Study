-- 1. Job 실행 이력 및 결과 확인 (가장 많이 씀)

SELECT
    I.JOB_NAME,              -- Job 이름 (csvImportJob)
    E.JOB_EXECUTION_ID,      -- 실행 ID (고유 번호)
    E.STATUS,                -- 진행 상태 (COMPLETED, FAILED, STARTED 등)
    E.EXIT_CODE,             -- 종료 코드 (COMPLETED, FAILED)
    E.START_TIME,            -- 시작 시간
    E.END_TIME,              -- 종료 시간
    TIMEDIFF(E.END_TIME, E.START_TIME) as DURATION -- 소요 시간
FROM BATCH_JOB_INSTANCE I
         JOIN BATCH_JOB_EXECUTION E ON I.JOB_INSTANCE_ID = E.JOB_INSTANCE_ID
ORDER BY E.JOB_EXECUTION_ID DESC;

-- 2. Step 별 데이터 처리 수 확인 (매우 중요)
SELECT
    E.JOB_EXECUTION_ID,
    S.STEP_NAME,             -- Step 이름 (csvImportStep)
    S.STATUS,                -- Step 상태
    S.READ_COUNT,            -- 읽은 건수 (CSV 라인 수)
    S.FILTER_COUNT,          -- Processor에서 null 반환된 건수 (나이 < 30)
    S.WRITE_COUNT,           -- DB에 실제 저장된 건수
    S.COMMIT_COUNT,          -- 트랜잭션 커밋 횟수 (Chunk 사이즈에 따라 결정)
    S.ROLLBACK_COUNT         -- 실패해서 롤백된 횟수
FROM BATCH_JOB_EXECUTION E
         JOIN BATCH_STEP_EXECUTION S ON E.JOB_EXECUTION_ID = S.JOB_EXECUTION_ID
ORDER BY E.JOB_EXECUTION_ID DESC;

-- 3. 실패 원인 확인 (에러 로그)
SELECT
    E.JOB_EXECUTION_ID,
    S.STEP_NAME,
    S.EXIT_CODE,
    S.EXIT_MESSAGE           -- 에러 메시지 및 스택트레이스 (가장 중요)
FROM BATCH_JOB_EXECUTION E
         JOIN BATCH_STEP_EXECUTION S ON E.JOB_EXECUTION_ID = S.JOB_EXECUTION_ID
WHERE E.STATUS = 'FAILED'    -- 실패한 Job만 조회
ORDER BY E.JOB_EXECUTION_ID DESC;

#
SELECT
    J.JOB_NAME,
    P.PARAMETER_NAME,
    P.PARAMETER_VALUE,
    P.PARAMETER_TYPE,
    P.IDENTIFYING
FROM BATCH_JOB_EXECUTION_PARAMS P
         JOIN BATCH_JOB_EXECUTION E ON P.JOB_EXECUTION_ID = E.JOB_EXECUTION_ID
         JOIN BATCH_JOB_INSTANCE J ON E.JOB_INSTANCE_ID = J.JOB_INSTANCE_ID
WHERE J.JOB_NAME = 'csvImportJob_V3'
ORDER BY P.JOB_EXECUTION_ID DESC;

CREATE TABLE batch_error_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    job_name VARCHAR(255) NOT NULL COMMENT 'Job 이름',
    step_name VARCHAR(255) NOT NULL COMMENT 'Step 이름',
    error_message VARCHAR(1000) COMMENT '에러 메시지',
    content TEXT COMMENT '실패한 데이터 원본',
    error_date DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) COMMENT '에러 발생 시각'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- auto-generated definition
create table customer
(
    id         bigint auto_increment
        primary key,
    age        int                                not null,
    email      varchar(255)                       null,
    name       varchar(255)                       null,
    created_at datetime default CURRENT_TIMESTAMP null
);