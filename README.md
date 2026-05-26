# jarVuln

Java 第三方组件漏洞研究与最小可复现 POC 集合。覆盖 RCE、XXE、反序列化、SSTI 等典型供应链风险，并提供 Activiti/Flowable 真实攻击链仿真与 Spring Boot 靶场接口。

> 本项目正在结合 AI Agent 进行漏洞模式挖掘、PoC 自动生成与安全检测规则扩展，用于小米 MiMo Orbit 百万亿 Token 创造者激励计划申请。

## 项目亮点

- **多组件覆盖**：10+ 常见 Java 依赖（工作流引擎、报表、XML 解析、模板引擎、序列化库等）
- **多触发面研究**：同一引擎下 Timer / Listener / Condition / ScriptTask / 部署时机等 6+ 种 RCE 入口
- **真实攻击链**：Activiti REST 模型导入 → JSON 转换 → 部署的两步链复现
- **Web 化靶场**：Flowable（8081）、Velocity SSTI 模块可 HTTP 触发，便于 Agent 自动化验证
- **中文研究注释**：记录触发时机、绕过技巧（如 HTML 注释打断 `getClass`）、测试结论

## 漏洞清单

| 组件 | 漏洞类型 | 影响版本 | 模块路径 |
|:--|:--|:--|:--|
| Activiti | RCE / XXE | 全版本 | `src/main/java/com/mechoy/activiti/` |
| Flowable | RCE / 内存马 | 全版本 | `Flowable/` |
| JasperReports | RCE | ~ 6.21.3 | `src/main/java/com/mechoy/jasperreports/` |
| Apache POI OOXML | XXE | ~ 3.10-FINAL / ~ 4.10.0 | `src/main/java/com/mechoy/poiOoxml/` |
| JDOM | XXE | 全版本 | `src/main/java/com/mechoy/jdom/` |
| Hivemind | XXE | 1.x | `src/main/java/com/mechoy/hivemind/` |
| dom4j / DocumentBuilder | XXE | 多版本 | `XXE/` |
| XStream | 反序列化 RCE | 1.x 老版本 | `Xstream/` |
| Jackson | 反序列化 Gadget | 1.x / 2.x | `jackson/` |
| Apache Velocity | SSTI | 1.6.x | `Velocity/` |

## 模块结构

```
jarVuln/
├── src/                    # 根工程 POC（Activiti / Jasper / POI / JDOM / Hivemind）
├── Flowable/               # Spring Boot + Flowable 多种 BPMN RCE / 内存马
├── XXE/                    # dom4j SAXReader、JDK DocumentBuilder
├── Xstream/                # TemplatesImpl / UIDefaults 反序列化链
├── jackson/                # Gadget 构造与序列化工具
└── Velocity/               # Velocity SSTI Web 接口
```

## 快速复现

### Activiti RCE（类路径部署）

```bash
# 运行 ActivitiTest.testClassPath()
# BPMN 资源：src/main/resources/activiti/classPath.bpmn20.xml
```

### Flowable Web 靶场

```bash
cd Flowable && mvn spring-boot:run
# 部署：GET http://localhost:8081/leave/deploy/executionListener
# 启动：GET http://localhost:8081/leave/startProcess/executionListener/{processDefinitionKey}
```

### Velocity SSTI

```bash
cd Velocity && mvn spring-boot:run
# GET /ssti/velocity?template=#set($x='')...
```

## AI 驱动的后续规划

| 阶段 | 目标 | 预期 Token 用途 |
|:--|:--|:--|
| Phase 1 | 用 AI 批量分析 BPMN/jrxml/XML payload，提取漏洞模式特征 | 代码理解、模式归纳 |
| Phase 2 | 基于 MiMo 生成各组件 SCA/SAST 检测规则与修复建议 | 长上下文代码生成 |
| Phase 3 | 搭建 Java 组件漏洞 Agent，自动枚举攻击面并输出 PoC | Agent 多轮推理 |
| Phase 4 | 构建漏洞知识库数据集，支持 LLM 安全编码微调评测 | 批量标注与文档生成 |

## 使用 AI 工具

- **Cursor** + Claude / MiMo：POC 编写、BPMN payload 构造、模块重构
- **MiMo API**：计划接入 Cursor 作为主力 Coding 模型，用于大规模漏洞样本分析与规则生成

## 免责声明

本项目仅用于 **授权环境内的安全研究与教育**。所有 POC 含恶意逻辑，请勿在生产环境或未授权系统上运行。使用者需自行承担合规责任。

## 申请说明

小米 MiMo Orbit 申请表单可直接参考 [`MIMO_APPLICATION.md`](MIMO_APPLICATION.md)。
