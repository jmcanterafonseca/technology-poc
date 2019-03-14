#!/usr/bin/python3
# -*- coding: utf-8 -*-

import sys
import asyncio

import urllib.request
import urllib.parse
from datetime import datetime

entities = list()
entities.append('Museum-b24a98d7fd0e4f37947add846d75fc9b')
entities.append('Museum-9a8ea922993376094643e594265fe735')


def get_entity_data(entity_id, service):
    url = 'https://orion.lab.fiware.org/v2/entities/{}?options=keyValues'

    request = urllib.request.Request(url=url.format(entity_id), headers={'Fiware-Service': service})

    response = urllib.request.urlopen(request)

    return response.read().decode('utf-8')


async def get_entity(entity_id, service):
    return get_entity_data(entity_id, service)


async def main():
    print('hello')
    await asyncio.sleep(1)
    print('world. After 1 second')

    dt = datetime.now()

    t1 = get_entity(entities[0], 'poi')
    t2 = get_entity(entities[1], 'poi')

    results = await asyncio.gather(t1,t2)

    dt2 = datetime.now()

    print('Time consumed doing it asynchronously: ', dt2.microsecond - dt.microsecond)

    print(results[0])
    print(results[1])


async def launcher():
    await main()


def sync_retrieval():
    dt = datetime.now()

    get_entity_data(entities[0], 'poi')
    get_entity_data(entities[1], 'poi')

    dt2 = datetime.now()

    print('Time consumed doing it synchronously: ', dt2.microsecond - dt.microsecond)


if __name__ == '__main__':

    mode = 'async'

    if len(sys.argv) > 1:
        mode = sys.argv[1]

    if mode == 'async':
        asyncio.run(launcher())
    else:
        sync_retrieval()
