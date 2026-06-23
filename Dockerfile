# ==========================================
# 1. AŞAMA: Derleme (Build)
# ==========================================
# Maven ve Java 17 içeren resmi imajı kullanıyoruz. 
# (Eğer Java 21 kullanıyorsan 'temurin-17' kısmını 'temurin-21' yapmalısın)
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

# Container içinde çalışacağımız ana dizini belirliyoruz
WORKDIR /app

# ÖNEMLİ OPTİMİZASYON: Önce sadece pom.xml'i kopyalıyoruz.
# Böylece pom.xml değişmediği sürece Docker bağımlılıkları tekrar tekrar indirmez (Cache kullanır).
COPY pom.xml .
RUN mvn dependency:go-offline

# Şimdi projenin kaynak kodlarını kopyalıyoruz
COPY src ./src

# Projeyi derleyip .jar dosyasını oluşturuyoruz (Testleri atlayarak süreci hızlandırıyoruz)
RUN mvn clean package -DskipTests

# ==========================================
# 2. AŞAMA: Çalıştırma (Run)
# ==========================================
# Uygulamayı çalıştırmak için sadece JRE (Java Runtime Environment) içeren çok hafif bir imaj seçiyoruz.
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# İlk aşamada (builder) oluşturduğumuz .jar dosyasını bu yeni ve temiz imaja kopyalıyoruz
COPY --from=builder /app/target/*.jar app.jar

# (Opsiyonel) Container'ın hangi portta çalıştığını belirtir (Sadece bilgi amaçlıdır, asıl portu compose belirler)
EXPOSE 8080

# Container ayağa kalktığında çalıştırılacak komut
ENTRYPOINT ["java", "-jar", "app.jar"]