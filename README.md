# Java Summary（VuePress 文档项目）

面向 Java 学习与工程实践的知识库，按三层组织：**基础 -> 进阶 -> 大神**。  
现在已改为 VuePress 文档项目，可通过侧边栏进行系统化阅读。

## 快速启动（本地预览）

```bash
npm install
npm run docs:dev
```

启动后在浏览器访问终端输出的本地地址（通常是 `http://localhost:8080`）。

## 构建静态站点

```bash
npm run docs:build
```

构建结果目录：`docs/.vuepress/dist/`

## GitHub Pages 自动部署

项目已内置工作流：[`/.github/workflows/deploy-docs.yml`](./.github/workflows/deploy-docs.yml)  
推送到 `main` 后会自动构建并发布到 GitHub Pages。

## Vercel 部署（推荐参数）

项目已提供：[`/vercel.json`](./vercel.json)，可直接被 Vercel 识别。  
如果你在 Vercel 控制台手动配置，请使用：

- Build Command: `npm run build`
- Output Directory: `docs/.vuepress/dist`
- Install Command: `npm install`

说明：当前 VuePress `base` 默认是 `/`，适配 Vercel；GitHub Pages 通过环境变量 `VUEPRESS_BASE=/Java-Summary/` 注入。

## 文档入口

- 总索引：[`docs/README.md`](./docs/README.md)
- 基础：[`docs/基础/README.md`](./docs/基础/README.md)
- 进阶：[`docs/进阶/README.md`](./docs/进阶/README.md)
- 大神：[`docs/大神/README.md`](./docs/大神/README.md)

## 结构说明

- VuePress 配置：[`docs/.vuepress/config.js`](./docs/.vuepress/config.js)
- 规则约定：[`AGENTS.md`](./AGENTS.md)
- 示例代码：[`examples/`](./examples/)
