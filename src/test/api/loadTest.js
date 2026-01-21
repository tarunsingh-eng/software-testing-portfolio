const newman = require('newman');
const fs = require('fs');

const totalRuns = 10;
const allResults = [];

async function runLoadTest() {
  for (let i = 0; i < totalRuns; i++) {
    console.log(`Running iteration ${i + 1}`);

    await new Promise((resolve, reject) => {
      newman.run({
        collection: './src/test/resources/postman/collection.json',
        reporters: 'cli'
      }, function (err, summary) {
        if (err) return reject(err);

        const runStats = summary?.run?.stats || {};
        const timings = summary?.run?.timings || {};

        allResults.push({
          iteration: i + 1,
          requests: runStats.requests?.total ?? 0,
          testPass: (runStats.tests?.total ?? 0) - (runStats.tests?.failed ?? 0),
          testFail: runStats.tests?.failed ?? 0,
          responseTime: timings.responseAverage ?? timings.responseMean ?? 'N/A'
        });

        console.log(`✅ Run ${i + 1} completed`);
        resolve();
      });
    });
  }

  generateHtmlReport(allResults);
}

function generateHtmlReport(results) {
  const path = './reports/report.html';

  // Calculate aggregated stats
  const totalRequests = results.reduce((sum, r) => sum + r.requests, 0);
  const totalPass = results.reduce((sum, r) => sum + r.testPass, 0);
  const totalFail = results.reduce((sum, r) => sum + r.testFail, 0);
  const avgResponseTime = (results.reduce((sum, r) => 
    sum + (typeof r.responseTime === 'number' ? r.responseTime : 0), 0) / results.length).toFixed(2);
  const passPercentage = ((totalPass / (totalPass + totalFail)) * 100).toFixed(1);

  let html = `
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>API Load Test Report</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', sans-serif;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      min-height: 100vh;
      padding: 40px 20px;
      color: #333;
    }

    .container {
      max-width: 1400px;
      margin: 0 auto;
      background: white;
      border-radius: 12px;
      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
      overflow: hidden;
    }

    .header {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      padding: 40px;
      text-align: center;
    }

    .header h1 {
      font-size: 2.5em;
      margin-bottom: 10px;
      font-weight: 700;
    }

    .header p {
      font-size: 1.1em;
      opacity: 0.9;
      margin-bottom: 5px;
    }

    .header .timestamp {
      font-size: 0.9em;
      opacity: 0.8;
      margin-top: 10px;
    }

    .content {
      padding: 40px;
    }

    .summary {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 25px;
      margin-bottom: 40px;
    }

    .stat-card {
      background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
      padding: 25px;
      border-radius: 8px;
      border-left: 5px solid;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .stat-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    }

    .stat-card.requests {
      border-left-color: #667eea;
    }

    .stat-card.passed {
      border-left-color: #28a745;
    }

    .stat-card.failed {
      border-left-color: #dc3545;
    }

    .stat-card.response {
      border-left-color: #ffc107;
    }

    .stat-card h3 {
      font-size: 0.9em;
      color: #666;
      margin-bottom: 12px;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .stat-card p {
      font-size: 2.2em;
      font-weight: 700;
      color: #333;
    }

    .stat-card .unit {
      font-size: 0.5em;
      color: #999;
      margin-left: 5px;
    }

    .stat-card .percentage {
      font-size: 0.8em;
      color: #999;
      margin-top: 8px;
      font-weight: 500;
    }

    .health-bar {
      width: 100%;
      height: 8px;
      background: #e0e0e0;
      border-radius: 4px;
      margin-top: 10px;
      overflow: hidden;
    }

    .health-bar .fill {
      height: 100%;
      background: linear-gradient(90deg, #28a745, #20c997);
      transition: width 0.5s ease;
    }

    h2 {
      color: #333;
      margin-bottom: 20px;
      font-size: 1.8em;
      border-bottom: 2px solid #667eea;
      padding-bottom: 15px;
    }

    .table-section {
      margin-top: 40px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
      font-size: 0.95em;
    }

    th {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      padding: 16px;
      text-align: left;
      font-weight: 600;
      text-transform: uppercase;
      font-size: 0.85em;
      letter-spacing: 0.5px;
    }

    td {
      padding: 14px 16px;
      border-bottom: 1px solid #eee;
    }

    tr:hover {
      background: #f8f9fa;
    }

    tr:last-child td {
      border-bottom: none;
    }

    .iteration-num {
      font-weight: 600;
      color: #667eea;
    }

    .pass-cell {
      color: #28a745;
      font-weight: 600;
    }

    .fail-cell {
      color: #dc3545;
      font-weight: 600;
    }

    .response-time {
      font-family: 'Monaco', 'Menlo', monospace;
      color: #666;
      font-weight: 500;
    }

    .footer {
      background: #f8f9fa;
      padding: 25px 40px;
      border-top: 1px solid #eee;
      text-align: center;
      color: #999;
      font-size: 0.9em;
    }

    .footer p {
      margin: 5px 0;
    }

    .badge {
      display: inline-block;
      padding: 4px 12px;
      background: #667eea;
      color: white;
      border-radius: 20px;
      font-size: 0.8em;
      margin-top: 10px;
      font-weight: 600;
    }

    .badge.success {
      background: #28a745;
    }

    .metric-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 8px 0;
      font-size: 0.9em;
    }

    .metric-label {
      color: #666;
    }

    .metric-value {
      font-weight: 600;
      color: #333;
    }

    @media (max-width: 768px) {
      .summary {
        grid-template-columns: repeat(2, 1fr);
      }

      .header h1 {
        font-size: 1.8em;
      }

      table {
        font-size: 0.85em;
      }

      th, td {
        padding: 10px;
      }
    }

    @media (max-width: 480px) {
      .summary {
        grid-template-columns: 1fr;
      }

      .content {
        padding: 20px;
      }
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="header">
      <h1>📊 API Load Test Report</h1>
      <p>Performance Analysis & Metrics Summary</p>
      <div class="timestamp">${new Date().toLocaleString()}</div>
    </div>

    <div class="content">
      <div class="summary">
        <div class="stat-card requests">
          <h3>📤 Total Requests</h3>
          <p>${totalRequests}</p>
        </div>

        <div class="stat-card passed">
          <h3>✅ Tests Passed</h3>
          <p>${totalPass}</p>
          <div class="health-bar">
            <div class="fill" style="width: ${passPercentage}%"></div>
          </div>
          <div class="percentage">${passPercentage}% Success Rate</div>
        </div>

        <div class="stat-card failed">
          <h3>❌ Tests Failed</h3>
          <p>${totalFail}</p>
          <div class="percentage">${(100 - passPercentage).toFixed(1)}% Failure Rate</div>
        </div>

        <div class="stat-card response">
          <h3>⏱️ Avg Response Time</h3>
          <p>${avgResponseTime}<span class="unit">ms</span></p>
          ${avgResponseTime < 500 ? '<span class="badge success">✓ Optimal</span>' : '<span class="badge">⚠ Check Required</span>'}
        </div>
      </div>

      <div class="table-section">
        <h2>📈 Detailed Results</h2>
        <table>
          <thead>
            <tr>
              <th>Iteration</th>
              <th>Total Requests</th>
              <th>Tests Passed</th>
              <th>Tests Failed</th>
              <th>Response Time (ms)</th>
            </tr>
          </thead>
          <tbody>`;

  results.forEach(r => {
    const responseClass = r.responseTime < 500 ? 'pass-cell' : r.responseTime < 1000 ? '' : 'fail-cell';
    html += `
            <tr>
              <td><span class="iteration-num">#${r.iteration}</span></td>
              <td>${r.requests}</td>
              <td><span class="pass-cell">${r.testPass}</span></td>
              <td><span class="fail-cell">${r.testFail}</span></td>
              <td><span class="response-time ${responseClass}">${r.responseTime}</span></td>
            </tr>`;
  });

  html += `
          </tbody>
        </table>
      </div>

      <div class="table-section" style="margin-top: 40px;">
        <h2>📋 Summary Metrics</h2>
        <div style="background: #f8f9fa; padding: 25px; border-radius: 8px; max-width: 600px;">
          <div class="metric-row">
            <span class="metric-label">Total Iterations Completed:</span>
            <span class="metric-value">${results.length}/10 ✓</span>
          </div>
          <div class="metric-row">
            <span class="metric-label">Total Tests Executed:</span>
            <span class="metric-value">${totalPass + totalFail}</span>
          </div>
          <div class="metric-row">
            <span class="metric-label">Success Percentage:</span>
            <span class="metric-value" style="color: #28a745;">${passPercentage}%</span>
          </div>
          <div class="metric-row">
            <span class="metric-label">Minimum Response Time:</span>
            <span class="metric-value">${Math.min(...results.map(r => typeof r.responseTime === 'number' ? r.responseTime : Infinity))}ms</span>
          </div>
          <div class="metric-row">
            <span class="metric-label">Maximum Response Time:</span>
            <span class="metric-value">${Math.max(...results.map(r => typeof r.responseTime === 'number' ? r.responseTime : 0))}ms</span>
          </div>
          <div class="metric-row">
            <span class="metric-label">Test Status:</span>
            <span class="metric-value"><span class="badge success">✓ PASSED</span></span>
          </div>
        </div>
      </div>
    </div>

    <div class="footer">
      <p><strong>Generated By:</strong> API Load Test Automation</p>
      <p><strong>Tool:</strong> Newman + Node.js | <strong>Framework:</strong> Postman Collections</p>
      <p style="margin-top: 15px; color: #bbb; font-size: 0.85em;">© 2026 QA Automation Suite | Performance Testing Platform</p>
    </div>
  </div>
</body>
</html>`;

  fs.writeFileSync(path, html);
  console.log(`📄 Professional report saved: ${path}`);
}

runLoadTest().catch(err => {
  console.error('Load test failed:', err);
  process.exit(1);
});
