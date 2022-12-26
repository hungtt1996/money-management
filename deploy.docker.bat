call mvn -DskipTests package -Pprod verify jib:dockerBuild
call docker run --name money_management -p 8081:8080 -d money_management:latest
