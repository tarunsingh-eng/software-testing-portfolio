const fs = require('fs');
const path = require('path');
const xml2js = require('xml2js');

/* =========================
   LOAD TEST DATA
========================= */
let loadData = { totalRuns: 0, avgResponse: 0 };

try {
  loadData = require('../../../docs/load-data.json');
} catch (e) {
  console.warn(' load-data.json not found, using defaults');
}

/* =========================
   API SMOKE TEST DATA
========================= */
let smokeStats = { total: 0, failed: 0 };

try {
  const smoke = JSON.parse(fs.readFileSync('docs/smoke-report.json'));
  smokeStats = smoke.run.stats.tests;
} catch (e) {
  console.warn(' smoke-report.json not found, using defaults');
}

/* =========================
   TEST RESULTS (JUnit XML)
========================= */
async function readTestResults() {

  const dir = 'target/surefire-reports';

  if (!fs.existsSync(dir)) {
    return {
      selenium: { total: 0, failed: 0 },
      cucumber: { total: 0, failed: 0 }
    };
  }

  const files = fs.readdirSync(dir).filter(f => f.endsWith('.xml'));

  let selenium = { total: 0, failed: 0 };
  let cucumber = { total: 0, failed: 0 };

  for (const file of files) {

    const xml = fs.readFileSync(path.join(dir, file), 'utf-8');
    const result = await xml2js.parseStringPromise(xml);

    let suites = [];

    if (result.testsuite) {
      suites = [result.testsuite];
    } else if (result.testsuites?.testsuite) {
      suites = result.testsuites.testsuite;
    }

    for (const suite of suites) {

      if (!suite.$) continue;

      const tests = Number(suite.$.tests || 0);
      const failures = Number(suite.$.failures || 0);

      /* Separate Cucumber from Selenium */
      if (file.toLowerCase().includes('testrunner') || file.toLowerCase().includes('cucumber')) {

        cucumber.total += tests;
        cucumber.failed += failures;

      } else {

        selenium.total += tests;
        selenium.failed += failures;

      }
    }
  }

  return { selenium, cucumber };
}

/* =========================
   BUILD HTML
========================= */
(async () => {

  const results = await readTestResults();

  const selenium = results.selenium;
  const cucumber = results.cucumber;

  const seleniumPassRate = selenium.total
    ? Math.round(((selenium.total - selenium.failed) / selenium.total) * 100)
    : 0;

  const cucumberPassRate = cucumber.total
    ? Math.round(((cucumber.total - cucumber.failed) / cucumber.total) * 100)
    : 0;

  const html = `
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>QA Automation Dashboard</title>

<style>
body {
  font-family: system-ui;
  background: #f6f8fa;
  padding: 40px;
}

h1 {
  margin-bottom: 30px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.card {
  background: white;
  padding: 24px;
  border-radius: 10px;
  box-shadow: 0 4px 14px rgba(0,0,0,0.08);
}

.metric {
  font-size: 32px;
  font-weight: bold;
  margin-top: 10px;
}

.label {
  color: #57606a;
  margin-top: 6px;
}

.ok {
  color: #2da44e;
}

.fail {
  color: #cf222e;
}

footer {
  margin-top: 40px;
  color: #57606a;
  font-size: 14px;
}
</style>

</head>

<body>

<h1> QA Automation Dashboard</h1>

<div class="grid">

<div class="card">
<h2>API Smoke Tests</h2>
<div class="metric">${smokeStats.total}</div>
<div class="label">Total Tests</div>
<div class="metric ${smokeStats.failed ? 'fail' : 'ok'}">${smokeStats.failed}</div>
<div class="label">Failures</div>
</div>

<div class="card">
<h2>API Load Tests</h2>
<div class="metric">${loadData.totalRuns ?? 0}</div>
<div class="label">Total Runs</div>
<div class="metric">${loadData.avgResponse ?? 0} ms</div>
<div class="label">Average Response</div>
</div>

<div class="card">
<h2>Selenium UI Tests</h2>
<div class="metric">${selenium.total}</div>
<div class="label">Total Tests</div>
<div class="metric ${selenium.failed ? 'fail' : 'ok'}">${selenium.failed}</div>
<div class="label">Failures</div>
<div class="metric ok">${seleniumPassRate}%</div>
<div class="label">Pass Rate</div>
</div>

<div class="card">
<h2>Cucumber BDD Scenarios</h2>
<div class="metric">${cucumber.total}</div>
<div class="label">Total Scenarios</div>
<div class="metric ${cucumber.failed ? 'fail' : 'ok'}">${cucumber.failed}</div>
<div class="label">Failures</div>
<div class="metric ok">${cucumberPassRate}%</div>
<div class="label">Pass Rate</div>
</div>

</div>

<footer>
Generated on ${new Date().toLocaleString()}
</footer>

</body>
</html>
`;

  fs.writeFileSync('docs/index.html', html);

  console.log('✅ Unified report generated: docs/index.html');

})();