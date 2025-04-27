# Ubuntu 기반으로 시작
FROM ubuntu:20.04

# 필요한 패키지 설치
RUN apt-get update && \
    apt-get install -y python3 python3-pip libzbar0 && \
    apt-get clean

# 작업 디렉토리 설정
WORKDIR /app

# 현재 디렉토리 파일들 복사
COPY . .

# Python 패키지 설치
RUN pip3 install -r requirements.txt

# 서버 실행
CMD ["python3", "server.py"]
