FROM ubuntu:20.04

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    libzbar0 \
    libzbar-dev \
    && apt-get clean

# LD_LIBRARY_PATH 환경변수 설정
ENV LD_LIBRARY_PATH=/usr/lib:/usr/lib/x86_64-linux-gnu

# 작업 디렉토리 설정
WORKDIR /app

# 소스코드 복사
COPY . .

# Python 패키지 설치
RUN pip3 install --upgrade pip
RUN pip3 install -r requirements.txt

# 서버 실행
CMD ["python3", "server.py"]
