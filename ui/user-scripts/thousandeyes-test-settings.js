// ==UserScript==
// @name         Test Settings
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://app.thousandeyes.com/settings/tests/?testId=3804339&tab=settings
// @grant        none
// ==/UserScript==

(function() {
    'use strict';

    window.addEventListener('load', () => {
        console.info('angularjs app instance test');
        window.setTimeout(() => {
            window.app = getAngularInstance();
        }, 1000);
    });


    function getAngularInstance() {
        console.group('getVueAppInstance');

        window.setTimeout(() => {
            attachSecurityTabOnClick();
        }, 100);
    }

    function attachSecurityTabOnClick() {
        let advancedTab = document.querySelector("#main-container > cea-tests > div > div > div.tab-pane.active > cea-test-settings > div > test-table > div > div.w-100.tests-table > accordion > div > div > div.accordion-body.show.collapse.in > div > div > div > test-table-row > div > form > ng-include > div > div > div.span17 > div > div > ul > li:nth-child(2) > a");
        advancedTab.onclick = function(){
            window.setTimeout(() => {
                let tabPane = document.querySelector("#main-container > cea-tests > div > div > div.tab-pane.active > cea-test-settings > div > test-table > div > div.w-100.tests-table > accordion > div > div > div.accordion-body.show.collapse.in > div > div > div > test-table-row > div > form > ng-include > div > div > div.span17 > div > div > div > div.tab-pane.active > ng-include");
                tabPane.innerHtml
                tabPane.appendChild(checkboxCreate());
            }, 10);
        };
    }

    function checkboxCreate() {
        var contentContainerDiv = document.createElement('div');
        contentContainerDiv.classList.add('row');
        contentContainerDiv.classList.add('no-gutters');
        contentContainerDiv.classList.add('form-horizontal');
        var contentChildDiv = document.createElement('div');
        contentChildDiv.classList.add('col-24');
        contentChildDiv.classList.add('subpanel');
        contentChildDiv.classList.add('border-top');
        contentChildDiv.classList.add('border-light');

        var titleDiv = document.createElement('div');
        titleDiv.classList.add('title');
        titleDiv.textContent = "Security Assurance";

        var controlGroupDiv = document.createElement('div');
        controlGroupDiv.classList.add('control-group');


        var checkboxDiv = document.createElement('div');
        checkboxDiv.classList.add('controls');
        checkboxDiv.classList.add('panel-group-controls');

        var checkboxLabel = document.createElement('label');
        checkboxLabel.classList.add('checkbox');
        checkboxLabel.setAttribute('style', 'margin-top: 5px');
        var checkboxInput = document.createElement('input');
        checkboxInput.classList.add('ng-not-empty');
        checkboxInput.classList.add('ng-pristine');
        checkboxInput.classList.add('ng-untouched');
        checkboxInput.classList.add('ng-valid');
        checkboxInput.classList.add('_ar_hide');
        checkboxInput.checked= true;

        checkboxInput.setAttribute('type', 'checkbox');
        var text= document.createTextNode("   Enable Security Assurance");

        checkboxLabel.appendChild(checkboxInput);
        checkboxLabel.appendChild(text);
        checkboxDiv.append(checkboxLabel);

        controlGroupDiv.appendChild(checkboxDiv);

        contentChildDiv.appendChild(titleDiv);
        contentChildDiv.appendChild(controlGroupDiv);

        contentContainerDiv.append(contentChildDiv);
        return contentContainerDiv;
    }


})();
