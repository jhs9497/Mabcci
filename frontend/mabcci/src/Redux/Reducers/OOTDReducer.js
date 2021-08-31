import _ from 'lodash';
import {
  OOTD_ALL,
  OOTD_FILTERING,
  OOTD_FILTER_STATE,
  OOTD_LIKE,
} from '../Type/OOTDType';
import { data } from '../data';

const initialState = {
  filter: 'all',
  ootd: [],
};

const OotdReducer = (state = initialState, { type, payload }) => {
  switch (type) {
    case OOTD_ALL: {
      return { ...state, ootd: _.uniqBy([...state.ootd, ...payload], 'id') };
    }
    case OOTD_FILTERING: {
      return { ...state, ootd: payload };
    }
    case OOTD_FILTER_STATE: {
      return { ...state, filter: payload };
    }
    case OOTD_LIKE: {
      const feedCopy = state.ootd;

      state.ootd.map((feed, idx) => {
        if (feed.id === Number(payload.ootdcontentId)) {
          if (payload.ootdcontentLike) feedCopy[idx].likeCount -= 1;
          else feedCopy[idx].likeCount += 1;
        }
        return feedCopy;
      });

      return { ...state, ootd: feedCopy };
    }

    default:
      return state;
  }
};

export default OotdReducer;
