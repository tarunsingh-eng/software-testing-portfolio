const fs = require('fs');
const path = require('path');
const xml2js = require('xml2js');

// ---------- LOAD TEST ----------
const loadData = require('../../../docs/load-data.json'); 

// ---------- SMOKE TEST ----------
const smoke = JSON.parse(fs.readFileSync('docs/smoke-report.json'));
const smokeStats = smoke.run.stats.tests;

// ---------- SELENIUM ----------
async function readSeleniumResults() {
  const dir = 'target/surefire-reports';

  if (!fs.existsSync(dir)) {
    return { total: 0, failed: 0 };
  }

  const files = fs.readdirSync(dir).filter(f => f.endsWith('.xml'));

  let total = 0;
  let failed = 0;

  for (const file of files) {
    const xml = fs.readFileSync(path.join(dir, file), 'utf-8');
    const result = await xml2js.parseStringPromise(xml);

    // JUnit format safety
    let suites = [];

    if (result.testsuite) {
      suites = [result.testsuite];
    } else if (result.testsuites && result.testsuites.testsuite) {
      suites = result.testsuites.testsuite;
    }

    for (const suite of suites) {
      if (!suite.$) continue;

      total += Number(suite.$.tests || 0);
      failed += Number(suite.$.failures || 0);
    }
  }

  return { total, failed };
}


(async () => {
  const selenium = await readSeleniumResults();

  const html = `
<!DOCTYPE html>
<html>
<head>
  <title>QA Automation Report</title>
  <style>
    body { font-family: Arial; padding: 40px; }
    h1 { color: #333; }
    .card { padding: 20px; margin-bottom: 20px; border: 1px solid #ddd; }
    .ok { color: green; }
    .fail { color: red; }
  </style>
</head>
<body>

<h1>🧪 QA Automation Dashboard</h1>

<div class="card">
  <h2>API Smoke Tests</h2>
  <p>Total: ${smokeStats.total}</p>
  <p class="${smokeStats.failed ? 'fail' : 'ok'}">
    Failed: ${smokeStats.failed}
  </p>
</div>

<div class="card">
  <h2>API Load Tests</h2>
  <p>Total Runs: ${loadData.totalRuns}</p>
  <p>Avg Response: ${loadData.avgResponse} ms</p>
</div>

<div class="card">
  <h2>Selenium UI Tests</h2>
  <p>Total: ${selenium.total}</p>
  <p class="${selenium.failed ? 'fail' : 'ok'}">
    Failed: ${selenium.failed}
  </p>
</div>

<p><em>Generated on ${new Date().toLocaleString()}</em></p>

</body>
</html>
`;

  fs.writeFileSync('docs/index.html', html);
  console.log('✅ Unified report generated: docs/index.html');
})();
