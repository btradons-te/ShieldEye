// ==UserScript==
// @name         ThousandEyes ShieldEye PoC
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Hack Day 2023 idea around enhancing the tests with security context.
// @author       You
// @match        https://app.thousandeyes.com/view/cloud-and-enterprise-agents/*
// @grant        none
// ==/UserScript==

(function() {
    'use strict';
    
    window.addEventListener('load', () => {
        window.setTimeout(() => {
            window.app = getAngularInstance();
        }, 2000);
    });


    function getAngularInstance() {
        console.group('getAngularAppInstance');
        let state = getCurrentState()

        state.viewTabs.scenarioTabs.push({
            id: 'security',
            class: 'security-tab',
            getHeader: () => "Security Assurance"
        })

        window.setTimeout(() => {
            attachSecurityTabOnClick();
        }, 2000);
    }

    function attachSecurityTabOnClick() {
        let securityTab = document.querySelector("#main-container > div > div > div.bottom-row.te-panel.w-100 > div > div > ng-include > view-tabs > div > ul > li.security-tab > a");
        securityTab.onclick = function(){
            window.setTimeout(() => {
                createTableFromAPIResponse();
            }, 1000);
        };
        
        // Refresh the security tab on timeline click
        let timelineElement = document.querySelector("div.w-100.timeline-row.timeline-container");
        timelineElement.onclick = function(){
            window.setTimeout(() => {
                createTableFromAPIResponse();
            }, 1000);
        };
    }

    // Fetch the SDAVC test results for given time window and create table
    function createTableFromAPIResponse() {
        const windowStart = getRoundId()
        const windowSize = 5
        const url = `http://10.56.216.113:7070/security-scan?windowStart=${windowStart}&windowSize=${windowSize}&targetIp=10.56.197.79`

        console.log(`Calling SDAVC API with windowStart/windowSize: ${windowStart}/${windowSize}`)
        fetch(url).then(response => response.json())
            .then(response => {
                    let tabPane = document.querySelector("#main-container > div > div > div.bottom-row.te-panel.w-100 > div > div > ng-include > view-tabs > div > div > div.tab-pane.active > div > div > div");
                    // Clear any previous tab state
                    tabPane.innerHTML = "";
                    tabPane.append(tableCreate(response));
                }).catch(e => {
                    console.error(e);
                }).catch(error => {
            console.error(error);
        });
    }

    function tableCreate(apiResults) {
        var contentContainerDiv = document.createElement('div');
        contentContainerDiv.setAttribute("data-v-5a18f565","");
        contentContainerDiv.setAttribute("data-v-35ff5272","");
        contentContainerDiv.setAttribute("data-v-c6705e10","");
        contentContainerDiv.classList.add('content-container');

        var tableContainerDiv = document.createElement('div');
        tableContainerDiv.setAttribute("data-v-e75a8c08","");
        tableContainerDiv.setAttribute("data-v-29c47e1c","");
        tableContainerDiv.setAttribute("data-v-c6705e10","");
        tableContainerDiv.setAttribute("data-v-5a18f565","");
        tableContainerDiv.classList.add('te-table-container');
        tableContainerDiv.classList.add('te-table-version-1');
        tableContainerDiv.classList.add('te-table-padding-size-md');

        var tbl = document.createElement('table');
        tbl.setAttribute("data-v-e75a8c08","");
        tbl.classList.add('te-table');

        var thead = document.createElement('thead');
        thead.setAttribute("data-v-e75a8c08","");
        thead.classList.add('te-table-thead');
        thead.classList.add('multi-test-table-head');

        var trHead = document.createElement('tr');
        trHead.setAttribute("data-v-e75a8c08","");
        trHead.appendChild(createHeader("Name"));
        trHead.appendChild(createHeader("Date"));
        trHead.appendChild(createHeader("Type"));
        trHead.appendChild(createHeader("Description"));
        thead.appendChild(trHead);

        tbl.appendChild(thead);

        var tbdy = document.createElement('tbody');
        tbdy.setAttribute("data-v-e75a8c08", "");

        console.log(apiResults.targetScanResult);
        for (var targetScanResult of apiResults.targetScanResult) {
            console.log(targetScanResult);
            for (var result of targetScanResult.detectedAnomalies) {
                console.log(result);
                tbdy.appendChild(createRow(result));
                tbl.appendChild(tbdy);
            }
        }

        tableContainerDiv.append(tbl);
        contentContainerDiv.append(tableContainerDiv);
        return contentContainerDiv;
    }

    function createRow(result) {
        var tr = document.createElement('tr');
        tr.setAttribute('data-v-49b282d1', '');
        tr.setAttribute('data-v-e75a8c08', '');
        tr.setAttribute('data-v-49b282d1', '');
        tr.classList.add('te-table-row');
        tr.classList.add('te-table-row-clickable');
        tr.classList.add('row-focus-no-outline');

        tr.appendChild(createColumn("SD-AVC Test"));

        var d = new Date(result.lastHit * 1000);
        tr.appendChild(createColumn(d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds()));
        tr.appendChild(createColumn(result.detectionType));
        tr.appendChild(createColumn(result.description));

        return tr;
    }

    function createColumn(value) {
        var td = document.createElement('td');
        td.setAttribute('data-v-49b282d1', '');
        td.setAttribute('data-v-e75a8c08', '');
        td.classList.add('te-table-td');
        td.classList.add('te-table-column-align-left');
        td.classList.add('multi-test-table-column');
        td.append(value);
        return td;
    }

    function createHeader(name) {
        var th = document.createElement('th');
        th.setAttribute("data-v-12573e93","");
        th.setAttribute("data-v-e75a8c08","");
        th.classList.add('te-table-head');
        th.classList.add('te-table-th');
        th.classList.add('te-table-head-sortable');
        th.classList.add('te-table-column-sort-ascending');
        th.classList.add('te-table-head-style-1');
        th.classList.add('te-table-column-align-left');
        th.classList.add('multi-test-table-column');

        var divName = document.createElement('div');
        divName.setAttribute("data-v-12573e93", "");
        divName.classList.add('te-table-head-content');
        divName.append(name);

        var outerDiv = document.createElement('div');
        outerDiv.setAttribute("data-v-12573e93", "");
        outerDiv.classList.add('te-table-head-content-container');
        outerDiv.append(divName);

        th.appendChild(outerDiv);
        return th;
    }

    function getRoundId() {
        let state = getCurrentState()
        return state.roundId;
    }

    function getCurrentState() {
        return angular.element(document.body).injector().get('viewStore').getState();
    }

})();
