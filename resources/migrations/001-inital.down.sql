--  Copyright 2016-2017 Boundless, http://boundlessgeo.com
--
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
--
--  http://www.apache.org/licenses/LICENSE-2.0
--
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and
--  limitations under the License.

DROP TABLE IF EXISTS signal.stores CASCADE;
DROP TRIGGER IF EXISTS update_updated_at_stores ON signal.stores;
DROP TABLE IF EXISTS signal.users CASCADE;
DROP TRIGGER IF EXISTS update_updated_at_users ON signal.users;
DROP TABLE IF EXISTS signal.triggers;
DROP TRIGGER IF EXISTS update_updated_at_triggers ON signal.triggers;
DROP FUNCTION IF EXISTS signal.update_updated_at_column();

