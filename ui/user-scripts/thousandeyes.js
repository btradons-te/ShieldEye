// ==UserScript==
// @name         ThousandEyes ShieldEye PoC
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Hack Day 2023 idea around enhancing the tests with security context.
// @author       You
// @match        https://app.thousandeyes.com/view/cloud-and-enterprise-agents/*
// @icon         https://www.google.com/s2/favicons?sz=64&domain=thousandeyes.com
// @grant        none
// ==/UserScript==

const tabHTML = `
<div class="bottom-row te-panel w-100">
    <div ng-switch="selectors.isScenarioSwitching()">
        <div ng-switch-when="false">
            <ng-include src="selectors.getCurrentScenario().templateUrl"><view-tabs>
                    <div class="view-tabs tabbable" type="'pills'">
                        <ul class="nav nav-pills" ng-class="{'nav-stacked': vertical}" ng-transclude="">
                            <li ng-class="{active: active, disabled: disabled}"
                                ng-repeat="tab in selectors.getCurrentScenarioTabs()"
                                active="selectors.getViewTab(tab.id).isActive" select="actions.viewTabClicked(tab.id)"
                                class="map-tab active">
                                <a ng-click="select()" tab-heading-transclude=""><tab-heading
                                        ng-hide="selectors.isTabDisabled(tab.id)" ng-bind-html="tab.getHeader()">SD-AVC
                                        Security</tab-heading></a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" ng-repeat="tab in tabs" ng-class="{active: tab.active}"
                                tab-content-transclude="tab">
                                <table data-v-e75a8c08="" class="te-table">
                                    <thead data-v-e75a8c08="" class="te-table-thead multi-test-table-head">
                                        <tr data-v-e75a8c08="">
                                            <th data-v-12573e93="" data-v-e75a8c08="" tabindex="0"
                                                class="te-table-head te-table-th te-table-head-sortable te-table-column-sort-ascending te-table-head-style-1 te-table-column-align-left multi-test-table-column">
                                                <div data-v-12573e93="" class="te-table-head-content-container">
                                                    <div data-v-12573e93="" class="te-table-head-content">
                                                        Name
                                                    </div> <span data-v-3af26f61="" data-v-12573e93=""
                                                        aria-hidden="true"
                                                        class="te-cc-icon te-table-head-sort-icon te-cc-icon-block te-cc-icon-svg te-cc-icon-md"><svg
                                                            viewBox="0 0 16 16" xml:space="preserve">
                                                            <path fill="#274752"
                                                                d="M9.551 9.051v-7.05L6.552 2 6.55 9.051H3.101L8.05 14 13 9.051z">
                                                            </path>
                                                        </svg></span>
                                                </div>
                                            </th>
                                            <th data-v-12573e93="" data-v-e75a8c08="" tabindex="0"
                                                class="te-table-head te-table-th te-table-head-sortable te-table-column-sort-ascending te-table-head-style-1 te-table-column-align-left multi-test-table-column">
                                                <div data-v-12573e93="" class="te-table-head-content-container">
                                                    <div data-v-12573e93="" class="te-table-head-content">
                                                        Target
                                                    </div> <span data-v-3af26f61="" data-v-12573e93=""
                                                        aria-hidden="true"
                                                        class="te-cc-icon te-table-head-sort-icon te-cc-icon-block te-cc-icon-svg te-cc-icon-md"><svg
                                                            viewBox="0 0 16 16" xml:space="preserve">
                                                            <path fill="#274752"
                                                                d="M9.551 9.051v-7.05L6.552 2 6.55 9.051H3.101L8.05 14 13 9.051z">
                                                            </path>
                                                        </svg></span>
                                                </div>
                                            </th>
                                            <th data-v-12573e93="" data-v-e75a8c08="" tabindex="0"
                                                class="te-table-head te-table-th te-table-head-sortable te-table-column-sort-descending te-table-head-style-1 te-table-column-align-left multi-test-table-column">
                                                <div data-v-12573e93="" class="te-table-head-content-container">
                                                    <div data-v-12573e93="" class="te-table-head-content">
                                                        Date (CEST)
                                                    </div> <span data-v-3af26f61="" data-v-12573e93=""
                                                        aria-hidden="true"
                                                        class="te-cc-icon te-table-head-sort-icon te-cc-icon-block te-cc-icon-svg te-cc-icon-md"><svg
                                                            viewBox="0 0 16 16" xml:space="preserve">
                                                            <path fill="#274752"
                                                                d="M9.551 9.051v-7.05L6.552 2 6.55 9.051H3.101L8.05 14 13 9.051z">
                                                            </path>
                                                        </svg></span>
                                                </div>
                                            </th>
                                            <th data-v-12573e93="" data-v-e75a8c08="" tabindex="0"
                                                class="te-table-head te-table-th te-table-head-sortable te-table-column-sort-descending te-table-head-style-1 te-table-column-align-left multi-test-table-column">
                                                <div data-v-12573e93="" class="te-table-head-content-container">
                                                    <div data-v-12573e93="" class="te-table-head-content">
                                                        Vulnerability
                                                    </div> <span data-v-3af26f61="" data-v-12573e93=""
                                                        aria-hidden="true"
                                                        class="te-cc-icon te-table-head-sort-icon te-cc-icon-block te-cc-icon-svg te-cc-icon-md"><svg
                                                            viewBox="0 0 16 16" xml:space="preserve">
                                                            <path fill="#274752"
                                                                d="M9.551 9.051v-7.05L6.552 2 6.55 9.051H3.101L8.05 14 13 9.051z">
                                                            </path>
                                                        </svg></span>
                                                </div>
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody data-v-e75a8c08="">
                                        <tr data-v-49b282d1="" data-v-e75a8c08="" tabindex="0"
                                            class="te-table-row te-table-row-clickable row-focus-no-outline">
                                            <td data-v-e75a8c08="" data-v-49b282d1=""
                                                class="te-table-td te-table-column-align-left multi-test-table-column">
                                                <div data-v-13b92198="" data-v-69cd96b4="" data-v-4fea13ba=""
                                                    class="v-popover agent-tooltip" data-v-49b282d1=""><span
                                                        data-v-13b92198="" aria-describedby="popover_pgr86vnt77"
                                                        tabindex="-1" class="trigger"><span data-v-329807af=""
                                                            data-v-69cd96b4=""
                                                            class="views-agent-icon-container agent-icon-container agent-tooltip-icon"
                                                            data-v-13b92198=""><span data-v-3af26f61=""
                                                                data-v-329807af="" aria-hidden="true"
                                                                class="te-cc-icon views-agent-icon te-cc-icon-svg te-cc-icon-md"><svg
                                                                    viewBox="0 0 16 16">
                                                                    <path class="st1"
                                                                        d="M4.074 14.037A4.08 4.08 0 0 1 0 9.963C0 8.56.753 7.239 1.972 6.497c.044-1.455 1.19-2.57 2.593-2.57.35 0 .697.075 1.032.223a4.574 4.574 0 0 1 3.876-2.187 4.57 4.57 0 0 1 4.564 4.539C15.249 7.245 16 8.562 16 9.963a4.08 4.08 0 0 1-4.074 4.074H4.074zm.49-8.835c-.708 0-1.303.575-1.324 1.282l.005.09.001.656-.56.328c-.87.506-1.41 1.428-1.41 2.405a2.8 2.8 0 0 0 2.798 2.797h7.852a2.8 2.8 0 0 0 2.798-2.797c0-.977-.54-1.898-1.408-2.405l-.562-.328v-.725c-.041-1.829-1.515-3.266-3.281-3.266-1.179 0-2.239.621-2.839 1.663l-.53.924-.954-.476a1.311 1.311 0 0 0-.586-.148z"
                                                                        fill="#274752"></path>
                                                                    <path class="st0" stroke-linecap="round"
                                                                        stroke-miterlimit="10" d="m5.978 5.203.718.406"
                                                                        fill="none" stroke="#274752"></path>
                                                                </svg></span> <!----> <!----></span> </span> </div>
                                                <span data-v-4fea13ba="" data-v-49b282d1=""
                                                    class="multi-test-table-agent">SD-AVC Test</span>
                                            </td>
                                            <td data-v-e75a8c08="" data-v-49b282d1=""
                                                class="te-table-td te-table-column-align-left multi-test-table-column">
                                                <div data-v-4fea13ba="" data-v-49b282d1=""
                                                    class="multi-test-table-target-name">
                                                    Unknown
                                                </div>
                                                <div data-v-4fea13ba="" data-v-49b282d1=""
                                                    class="multi-test-table-target-ip">
                                                    10.10.10.10
                                                </div>
                                            </td>
                                            <td data-v-e75a8c08="" data-v-49b282d1=""
                                                class="te-table-td te-table-column-align-left multi-test-table-column">
                                                14:45:00
                                            </td>
                                            <td data-v-e75a8c08="" data-v-49b282d1=""
                                                class="te-table-td te-table-column-align-left multi-test-table-column">
                                                Weak Password Detected
                                            </td>
                                        </tr> 
                                    </tbody>
                                </table>
                            </div>
                        </div>
                </view-tabs> </ng-include>
        </div>
    </div>
</div>
`

// Helpers
function waitForElement(selector) {
  return new Promise((resolve) => {
    if (document.querySelector(selector)) {
      return resolve(document.querySelector(selector));
    }

    const observer = new MutationObserver((mutations) => {
      if (document.querySelector(selector)) {
        resolve(document.querySelector(selector));
        observer.disconnect();
      }
    });

    observer.observe(document.body, {
      childList: true,
    });
  });
}

// Main
(function () {
  "use strict";
  //Add custom tab with fake data when loading TE Test View page
  waitForElement('[ng-switch-default=""]').then((elm) => {
    elm.insertAdjacentHTML("beforeend", tabHTML);
  });
})();
