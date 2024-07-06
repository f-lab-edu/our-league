# test
# Amazon Corretto 17 이미지 사용
FROM amazoncorretto:17-alpine

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/league-0.0.1-SNAPSHOT.jar app.jar

# 한국 시간대 설정
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 포트 설정 (Spring Boot 기본 포트)
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-jar","/app/app.jar"]