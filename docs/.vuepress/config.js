const fs = require('fs');
const path = require('path');

const docsRoot = path.resolve(__dirname, '..');
const docsBase = process.env.VUEPRESS_BASE || '/';

function sortNames(names) {
  return names.sort((a, b) => a.localeCompare(b, 'zh-Hans-CN', { numeric: true, sensitivity: 'base' }));
}

function listMarkdownRoutes(sectionDir) {
  const sectionPath = path.join(docsRoot, sectionDir);
  if (!fs.existsSync(sectionPath)) {
    return [];
  }

  const fileNames = sortNames(
    fs
      .readdirSync(sectionPath)
      .filter((name) => name.endsWith('.md'))
  );

  const routes = [];
  if (fileNames.includes('README.md')) {
    routes.push(`/${sectionDir}/`);
  }

  for (const fileName of fileNames) {
    if (fileName === 'README.md') {
      continue;
    }
    routes.push(`/${sectionDir}/${fileName.replace(/\.md$/, '')}`);
  }

  return routes;
}

function listRootMarkdownRoutes() {
  const fileNames = sortNames(
    fs
      .readdirSync(docsRoot)
      .filter((name) => name.endsWith('.md') && name !== 'README.md')
  );

  return fileNames.map((fileName) => `/${fileName.replace(/\.md$/, '')}`);
}

const rootPrimary = [
  '/00-总览-学习路线',
  '/01-按学习顺序索引',
  '/02-按面试频率索引',
  '/03-按专题索引'
];

const rootPrimarySet = new Set(rootPrimary);
const rootExtra = listRootMarkdownRoutes().filter((route) => !rootPrimarySet.has(route));

module.exports = {
  title: 'Java Summary',
  description: '基础 -> 进阶 -> 大神 的 Java 知识体系文档',
  // 支持不同部署环境复用：Vercel 默认 "/"，GitHub Pages 可注入 "/Java-Summary/"
  base: docsBase,
  locales: {
    '/': {
      lang: 'zh-CN'
    }
  },
  themeConfig: {
    repo: 'ZhcChen/Java-Summary',
    docsDir: 'docs',
    smoothScroll: true,
    lastUpdated: '最后更新',
    nav: [
      { text: '首页', link: '/' },
      { text: '基础', link: '/基础/' },
      { text: '进阶', link: '/进阶/' },
      { text: '大神', link: '/大神/' },
      { text: '学习顺序', link: '/01-按学习顺序索引' }
    ],
    sidebar: {
      '/基础/': [
        {
          title: '基础主线（书籍版）',
          collapsable: false,
          children: listMarkdownRoutes('基础')
        },
        {
          title: '基础扩展（L1）',
          collapsable: true,
          children: listMarkdownRoutes('基础/L1-初级')
        }
      ],
      '/进阶/': [
        {
          title: '进阶入口',
          collapsable: false,
          children: listMarkdownRoutes('进阶')
        },
        {
          title: '进阶（L2-中级）',
          collapsable: true,
          children: listMarkdownRoutes('进阶/L2-中级')
        },
        {
          title: '进阶（L3-高级）',
          collapsable: true,
          children: listMarkdownRoutes('进阶/L3-高级')
        }
      ],
      '/大神/': [
        {
          title: '大神入口',
          collapsable: false,
          children: listMarkdownRoutes('大神')
        }
      ],
      '/': [
        {
          title: '总览与索引',
          collapsable: false,
          children: ['/', ...rootPrimary]
        },
        {
          title: '专题与模板',
          collapsable: true,
          children: rootExtra
        }
      ]
    }
  },
  markdown: {
    lineNumbers: true
  },
  plugins: [
    ['mermaidjs']
  ]
};
