import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities } from './category.reducer';

export const Category = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const categoryList = useAppSelector(state => state.category.entities);
  const loading = useAppSelector(state => state.category.loading);

  const [parentCategories, setParentCategories] = useState([]);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  useEffect(() => {
    setParentCategories(categoryList.filter(c => c.level === 0));
  }, [categoryList]);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="category-heading" data-cy="CategoryHeading">
        <Translate contentKey="moneyManagementApp.category.home.title">Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="moneyManagementApp.category.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/category/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="moneyManagementApp.category.home.createLabel">Create new Category</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {categoryList && categoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="moneyManagementApp.category.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.nameVi">Name Vi</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.nameEn">Name En</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.icon">Icon</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.groupId">Group Id</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.parentId">Parent Id</Translate>
                </th>
                <th>
                  <Translate contentKey="moneyManagementApp.category.level">Level</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {categoryList.map((category, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/category/${category.id}`} color="link" size="sm">
                      {category.id}
                    </Button>
                  </td>
                  <td>{category.code}</td>
                  <td>{category.nameVi}</td>
                  <td>{category.nameEn}</td>
                  <td>
                    <img src={category.icon} alt="icon" width="48px" height="48px" />
                  </td>
                  <td>{category.groupId}</td>
                  <td>{parentCategories.find(p => p.id === category.parentId)?.nameVi}</td>
                  <td>{category.level}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/category/${category.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/category/${category.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/category/${category.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="moneyManagementApp.category.home.notFound">No Categories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Category;
