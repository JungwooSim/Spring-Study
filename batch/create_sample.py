import csv
import random

# 데이터 생성 설정
filename = "sample_data_200k.csv"
count = 200000

# 샘플 데이터 소스 (조합하여 다양성 확보)
first_names = ["John", "Jane", "Bob", "Alice", "Tom", "David", "Sarah", "Michael", "Emily", "Chris", "Jessica", "Daniel", "Laura", "Kevin", "Sophia"]
last_names = ["Doe", "Smith", "Johnson", "Brown", "Wilson", "Lee", "Kim", "Park", "Miller", "Davis", "Garcia", "Rodriguez", "Martinez", "Hernandez", "Lopez"]
domains = ["example.com", "test.com", "sample.com", "demo.com", "email.com", "site.org", "service.net"]

def generate_email(name, domain):
    # 이름의 공백을 없애고 소문자로 변환하여 이메일 생성
    clean_name = name.replace(" ", "").lower()
    return f"{clean_name}@{domain}"

print(f"{count}개의 데이터를 생성 중입니다... 잠시만 기다려주세요.")

try:
    with open(filename, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)

        # 1. 헤더 작성
        writer.writerow(['name', 'email', 'age'])

        # 2. 데이터 20만개 생성
        for i in range(count):
            # 랜덤 조합
            f_name = random.choice(first_names)
            l_name = random.choice(last_names)
            full_name = f"{f_name} {l_name}"

            # 이메일은 이름 기반으로 하되, 중복 방지를 위해 뒤에 숫자를 붙일 수도 있음
            # 여기서는 깔끔함을 위해 도메인을 랜덤하게 섞음
            email_domain = random.choice(domains)
            # 20만개이므로 중복을 피하기 위해 인덱스나 랜덤 숫자를 이메일에 섞는 것이 안전함
            email = f"{f_name.lower()}.{l_name.lower()}.{random.randint(100, 9999)}@{email_domain}"

            age = random.randint(20, 60) # 20세~60세 랜덤

            writer.writerow([full_name, email, age])

            if (i + 1) % 50000 == 0:
                print(f"{i + 1}개 생성 완료...")

    print(f"\n성공! '{filename}' 파일이 생성되었습니다.")

except Exception as e:
    print(f"오류가 발생했습니다: {e}")