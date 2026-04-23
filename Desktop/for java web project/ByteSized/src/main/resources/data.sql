INSERT INTO blog_posts (id, title, content, cover_image_path, created_at)
VALUES (
    1,
    'Welcome to ByteSized: Why Java 25 Over Java 26',
    'For our first release, we chose Java 25 because it is the latest long-term support baseline with stronger ecosystem maturity, wider framework support, and lower operational risk for production deployments. Java 26 is exciting, but we prioritize predictable stability for a beginner-friendly monolithic platform that we can maintain confidently.',
    NULL,
    CURRENT_TIMESTAMP
);

INSERT INTO comments (id, author_name, message, created_at, blog_post_id)
VALUES (
    1,
    'ByteSized Team',
    'Starting from a stable baseline helps us focus on learning architecture and shipping features consistently.',
    CURRENT_TIMESTAMP,
    1
);

ALTER TABLE blog_posts ALTER COLUMN id RESTART WITH 2;
ALTER TABLE comments ALTER COLUMN id RESTART WITH 2;
