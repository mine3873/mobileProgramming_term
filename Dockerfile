FROM ubuntu:20.04

# 필수 패키지 설치
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    libzbar0 \
    libzbar-dev \
    && apt-get clean

# 작업 디렉토리 설정
WORKDIR /app

# 현재 디렉토리 복사
COPY . .

# requirements.txt로 필요한 Python 패키지 설치
RUN pip3 install --upgrade pip
RUN pip3 install -r requirements.txt

# 서버 실행
CMD ["python3", "server.py"]
