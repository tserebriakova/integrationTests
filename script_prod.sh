#! /bin/bash
zip -r data.zip /opt/atlassian/pipelines/agent/build/build/allure-report/
FAILED=$(cat /opt/atlassian/pipelines/agent/build/build/allure-report/data/suites.csv | grep -c failed)
PASSED=$(cat /opt/atlassian/pipelines/agent/build/build/allure-report/data/suites.csv | grep -c passed)
BROKEN=$(cat /opt/atlassian/pipelines/agent/build/build/allure-report/data/suites.csv | grep -c broken)
TOTAL=$((FAILED+PASSED+BROKEN))
telegram_chat_id=-539201250

ALLURE_IDS_PROD=$(./allurectl launch list -p "${ALLURE_PROJECT_ID}" --no-header | grep "${CI_PIPELINE_ID}" | cut -d' ' -f1 || true)
ALLURE_ID_PROD=${ALLURE_IDS_PROD:7:3}
export ALLURE_ID_PROD

read -r -d '' message <<EOT
<b>-== PRODUCTION ENV ==-</b>
<b>TOTAL TESTS: ${TOTAL}</b>
<b>🟢 ${PASSED} PASSED TESTS</b>
<b>🔴 ${FAILED} FAILED TESTS</b>
<b>🟡 ${BROKEN} BROKEN TESTS</b>
<i>https://testops.ftr.group/launch/${ALLURE_ID_PROD}</i>
EOT

curl --data chat_id="${telegram_chat_id}" --data-urlencode "text='${message}'" "https://api.telegram.org/bot1843231899:AAHNY6VjxdVzjfehleTo9PhFl_Vhj74JdY8/sendMessage?parse_mode=HTML"
#curl -v -F "chat_id=-539201250" -F document=@data.zip https://api.telegram.org/bot1843231899:AAHNY6VjxdVzjfehleTo9PhFl_Vhj74JdY8/sendDocument
#curl -v -F "chat_id=-539201250" -F document=@/opt/atlassian/pipelines/agent/build/build/allure-report/data/suites.csv https://api.telegram.org/bot1843231899:AAHNY6VjxdVzjfehleTo9PhFl_Vhj74JdY8/sendDocument