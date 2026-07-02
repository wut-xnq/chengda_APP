# Git 笔记

## 一、Git 学习笔记

### 1.1 Git 三个区域

```
工作区 (Working Directory) ──git add──▶ 暂存区 (Staging Area) ──git commit──▶ 本地仓库 (Local Repo) ──git push──▶ 远程仓库 (Remote Repo)
```

| 区域 | 说明 |
|------|------|
| 工作区 | 你电脑上的实际文件目录 |
| 暂存区 | 临时存放即将提交的文件列表 |
| 本地仓库 | 已提交的历史记录（本地） |
| 远程仓库 | GitHub 上的仓库（云端） |

### 1.2 常用命令

#### 基础操作
```bash
git init                          # 初始化仓库
git clone <url>                   # 克隆远程仓库
git add <file>                    # 添加单个文件到暂存区
git add .                         # 添加所有修改到暂存区
git add -u                        # 只添加已跟踪文件的修改
git commit -m "说明"              # 提交到本地仓库
git status                        # 查看当前状态
git log                           # 查看提交历史
git log --oneline                 # 简洁模式查看历史
git log --oneline -5              # 只看最近5条
git log --stat                    # 查看每次修改了哪些文件
git log --follow <file>           # 查看某个文件的完整历史
```

#### 远程操作
```bash
git remote -v                     # 查看远程仓库地址
git remote add origin <url>       # 添加远程仓库
git push origin main              # 推送到远程 main 分支
git push -u origin main           # 首次推送（设置跟踪）
git pull origin main              # 拉取远程更新
git fetch                         # 只下载不合并
```

#### 分支操作
```bash
git branch                        # 查看本地分支
git branch -a                     # 查看所有分支（含远程）
git branch <name>                 # 创建新分支
git checkout <name>               # 切换分支
git checkout -b <name>            # 创建并切换
git merge <name>                  # 合并指定分支到当前分支
git branch -d <name>              # 删除分支
```

#### 撤销操作
```bash
git restore <file>                # 撤销工作区修改（恢复到最后一次提交）
git restore --staged <file>       # 取消暂存（从暂存区移出）
git reset --soft HEAD^            # 撤销最近一次 commit，保留暂存
git reset --hard HEAD^            # 撤销最近一次 commit，彻底丢弃
git revert <commit>               # 创建一个新提交来撤销某个 commit
```

### 1.3 LF 与 CRLF 换行符问题

**问题现象：**
```
warning: LF will be replaced by CRLF the next time Git touches it
```

**原因：**
- Linux/Mac 使用 `LF`（`\n`）作为换行符
- Windows 使用 `CRLF`（`\r\n`）作为换行符
- Git 在 Windows 上默认会自动转换

**解决：**
```bash
git config core.autocrlf true     # Windows 推荐：提交时转 LF，检出时转 CRLF
git config core.autocrlf input    # Mac/Linux：提交时转 LF，检出不转换
git config core.autocrlf false    # 不自动转换（适合纯文本/跨平台项目）
```

**结论：这个 warning 不影响代码，忽略即可。**

---

## 二、项目 Git 规范

### 2.1 仓库信息

| 项目 | 值 |
|------|-----|
| 仓库地址 | `https://github.com/wut-xnq/chengda_APP.git` |
| 默认分支 | `main` |
| Git 用户 | wut-xnq (2244129587@qq.com) |

### 2.2 项目结构

```
01-源码/
├── 后端服务/     # chengda-back（Java Spring Cloud 后端）
├── 后管web/      # cm-ui-master（Vue 后台管理前端）
└── 移动App/      # chengda-app-dev（uni-app 移动端）
```

### 2.3 提交信息规范（Commit Message）

格式：`type: description`

| type | 含义 | 示例 |
|------|------|------|
| `init` | 初始化项目/新增模块 | `init: 新增用户管理模块` |
| `feat` | 新增功能 | `feat: 添加充值功能` |
| `fix` | 修复 Bug | `fix: 修复登录页样式错位` |
| `refactor` | 重构代码 | `refactor: 重构请求拦截器` |
| `docs` | 修改文档 | `docs: 更新部署文档` |
| `style` | 代码格式（不影响逻辑） | `style: 统一缩进为2空格` |
| `chore` | 构建/工具变动 | `chore: 更新依赖版本` |

### 2.4 推荐工作流

**日常开发流程：**
```bash
# 1. 开始工作前，拉取最新代码
git pull origin main

# 2. 开发新功能
# ... 写代码 ...

# 3. 查看修改
git status
git diff                    # 查看具体改动

# 4. 提交
git add .
git commit -m "feat: 添加XX功能"

# 5. 推送到远程
git push origin main
```

**解决冲突：**
```bash
# 拉取时如果提示冲突
git pull origin main

# 1. 编辑冲突文件，解决冲突标记 <<<<<< / ====== / >>>>>>
# 2. 重新添加并提交
git add .
git commit -m "fix: 解决合并冲突"
```

---

## 三、本次操作记录

### 3.1 操作目标

将本地三个文件夹（后端服务、后管web、移动App）上传到 GitHub 仓库。

### 3.2 执行步骤

```bash
# Step 1: 进入项目目录
cd "D:/桌面/诚达资料/01-源码"

# Step 2: 查看当前状态
git status

# Step 3: 添加所有文件到暂存区
git add .

# Step 4: 提交到本地仓库
git commit -m "init: 上传后端服务、后管web、移动App"

# Step 5: 推送到 GitHub
git push origin main
```

### 3.3 推送结果

```
To https://github.com/wut-xnq/chengda_APP.git
   ac761e2..ac150d4  main -> main
```

推送成功，共 80 个对象，3.28 MiB。

### 3.4 遇到的问题

#### 问题 1：LF/CRLF 换行符警告
```
warning: LF will be replaced by CRLF the next time Git touches it
```
**解决：** 这是 Windows 上的正常行为，不影响代码，忽略即可。

#### 问题 2：后管web 文件夹在 GitHub 上显示为 3 天前
**原因：** 后管web 的文件修改仅是换行符差异（LF vs CRLF），实际代码内容没有变化。Git 提交时检测到内容无差异，因此没有产生新的提交记录。GitHub 显示的是该文件夹下最后一次**有实际内容变更**的提交时间（`3c2d00a` - 3 天前）。

**结论：** 代码已全部上传，不影响使用。如需更新日期，需对后管web做实际内容修改再提交。

### 3.5 提交历史

```
ac150d4 init:上传后端服务、后管web、移动App      ← 本次提交
ac761e2 Merge branch 'main' of ...
b615e75 fix: 修复图标路径，改为 static/app-icons
3c2d00a init: 诚达项目源码（后端+App前端+后管web）
16f868f Initial commit
```

---

## 四、常用场景速查

### 场景 1：首次关联远程仓库
```bash
git init
git remote add origin https://github.com/wut-xnq/chengda_APP.git
git add .
git commit -m "init: 初始化"
git push -u origin main
```

### 场景 2：修改后推送
```bash
git add .
git commit -m "fix: 修复XX问题"
git push origin main
```

### 场景 3：放弃本地修改，用远程覆盖
```bash
git fetch origin
git reset --hard origin/main
```

### 场景 4：查看某行代码是谁改的
```bash
git blame <file> -L 起始行,结束行
```

### 场景 5：回退到某个历史版本
```bash
git log --oneline                # 找到目标 commit id
git reset --hard <commit-id>     # 回退（会丢失之后的提交）
git push -f origin main          # 强制推送（慎用！）
```
