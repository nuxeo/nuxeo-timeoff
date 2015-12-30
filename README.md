# About Nuxeo Timeoff plugin

Nuxeo Timeoff is a holiday / timeoff request plugin. It deploys a [Polymer](https://www.polymer-project.org) based site to handle requests and all the managers / HR validation process. This plugin is only the website, all the business process is handled inside the Studio Project.

This whole application is built as a single app page, with several [Iron and Paper Polymer Components](https://elements.polymer-project.org/) and [Nuxeo Elements](https://github.com/nuxeo/nuxeo-elements) to communicate with Nuxeo. The only Nuxeo specific contribution is a [content enricher](https://doc.nuxeo.com/x/5wUuAQ) used to get user's profile avatar url if present.

# Description

## Roles

- Employee: an employee that do not manage anyone. It main goal in the application is to search and request timeoff.
- Manager: the manager can do the same as an employee and validate / reject / asking some changes to any pending request (see Prerequisites section).
- HR Manager: after the manager validation, the HR Manager must check if everything is legally good for the time-off.

## Features

- Managers / HR Managers dashboard that allow them to interact with employees requests (Validate / Reject / Ask for modification). A calendar view is available to compare the requested days with already accepted time off in the whole company.
- Managers / HR Managers are able to search for any employees holidays.
- Employees have a quick view on their pending requests, their incoming timeoffs and can refine search on them.

# Requirements

## Directory / User Group

 - A `hr_manager` group must be available containing the user that made the last HR validation.
 - The Nuxeo Package initiate a directory `EmployeeManagerMapping` that contains the Employee / Manager mapping. The `id` field is the employee username; and the `label` field is his manager username.
 
# Building

    mvn [-DskipCleanCache] clean install

## Deploying
 
### Manually

Too deploy on Nuxeo Platform manually: copy the builded JAR bundles into `$NUXEO_HOME/nxserver/bundles` and `nuxeo-timeoff-studio` Studio Project that contains workflows.

### Nuxeo Package

For now, a Nuxeo pakcage is available on his [dedicated repository](https://github.com/nuxeo/marketplace-nuxeo-timeoff) and at last will be available in the same repository.
 
# Resources
 
## Reporting issues
 
- [https://jira.nuxeo.com/browse/NXP/component/12500/](https://jira.nuxeo.com/browse/NXP/component/12500/)
- [https://jira.nuxeo.com/secure/CreateIssue!default.jspa?project=NXP](https://jira.nuxeo.com/secure/CreateIssue!default.jspa?project=NXP)
 
# Licensing
 
[GNU Lesser General Public License (LGPL) v2.1](http://www.gnu.org/licenses/lgpl-2.1.html)
 
# About Nuxeo
 
Nuxeo dramatically improves how content-based applications are built, managed and deployed, making customers more agile, innovative and successful. Nuxeo provides a next generation, enterprise ready platform for building traditional and cutting-edge content oriented applications. Combining a powerful application development environment with
SaaS-based tools and a modular architecture, the Nuxeo Platform and Products provide clear business value to some of the most recognizable brands including Verizon, Electronic Arts, Netflix, Sharp, FICO, the U.S. Navy, and Boeing. Nuxeo is headquartered in New York and Paris.
More information is available at [www.nuxeo.com](http://www.nuxeo.com).