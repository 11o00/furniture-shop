# Import test data with UTF-8 (avoid garbled Chinese on Windows)
$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
$sqlFile = Join-Path $projectRoot "data_import.sql"
$encodingFile = Join-Path $projectRoot "fix-encoding.sql"
$tempSql = Join-Path $env:TEMP "homemart_data_import.sql"

$mysqlHost = "127.0.0.1"
$mysqlPort = "3307"
$mysqlUser = "root"
$mysqlPass = "123456"
$database = "furnitureshop"

if (-not (Test-Path $sqlFile)) {
    Write-Error "data_import.sql not found"
}

$utf8NoBom = New-Object System.Text.UTF8Encoding $false
$content = [System.IO.File]::ReadAllText($sqlFile, $utf8NoBom)
[System.IO.File]::WriteAllText($tempSql, $content, $utf8NoBom)

Write-Host "Fixing database charset..."
Get-Content $encodingFile -Encoding UTF8 | mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser "-p$mysqlPass" --default-character-set=utf8mb4 $database
if ($LASTEXITCODE -ne 0) { Write-Error "fix-encoding.sql failed" }

Write-Host "Importing data..."
cmd /c "mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser -p$mysqlPass --default-character-set=utf8mb4 $database < `"$tempSql`""
if ($LASTEXITCODE -ne 0) { Write-Error "data_import.sql failed" }

Write-Host "Verify products:"
$verifySql = "SELECT id, name, description FROM products LIMIT 3;"
mysql -h $mysqlHost -P $mysqlPort -u $mysqlUser "-p$mysqlPass" --default-character-set=utf8mb4 $database -e $verifySql

Write-Host "Done."
