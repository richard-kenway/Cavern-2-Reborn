FROM eclipse-temurin:21-jdk

WORKDIR /workspace

# Keep wrapper and Gradle caches outside the bind mount for faster repeated builds.
ENV GRADLE_USER_HOME=/gradle-cache

CMD ["bash"]
