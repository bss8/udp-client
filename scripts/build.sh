#!/bin/bash
# Copyright (c) 2020. Borislav S. Sabotinov
# https://github.com/bss8
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

# Linux build script from source code
# NOTE:
# You will need to either have a lib directory at the project root with JUnit 5.4 dependencies
# or simply remove the test directory before building.

PKG_PATH=edu/txstate/bss64

find ../ -name "*.java" > sources_nix.txt
javac @sources_nix.txt

mkdir -p ../dist/${PKG_PATH}
mv ../src/main/java/${PKG_PATH}/*.class ../dist/${PKG_PATH}
cp ../src/META-INF/MANIFEST.MF ../dist/

cd ../dist
jar cmvf MANIFEST.MF client.jar edu/*
chmod 755 client.jar
cd ../scripts
